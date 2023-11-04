package com.grievanceredressalsystem.analyticsservice.services;

import com.grievanceredressalsystem.analyticsservice.mock.models.Ticket;
import com.grievanceredressalsystem.analyticsservice.mock.models.TicketStatus;
import com.grievanceredressalsystem.analyticsservice.models.MetricType;
import com.grievanceredressalsystem.analyticsservice.models.RangeFrequency;
import com.grievanceredressalsystem.analyticsservice.models.Report;
import com.grievanceredressalsystem.analyticsservice.repositories.ReportRepository;
import com.grievanceredressalsystem.analyticsservice.utils.StringDateComparator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AnalyticsServiceImpl implements AnalyticsService{
    public AnalyticsServiceImpl(@Qualifier("externalTicketService") ExternalTicketService ticketService,ReportRepository reportRepository) {
        this.ticketService = ticketService;
        this.reportRepository = reportRepository;
    }

    private ExternalTicketService ticketService;
    private ReportRepository reportRepository;

    private static boolean isDateInRange(Date date , Date startDate , Date endDate){
        return date.after(startDate) && date.before(endDate);
    }
    // Truncate the time part from a Date
    private static Date truncateTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
    @Override
    public Map<String, Long> getTicketsBasedOnStatus(Date startDate, Date endDate, RangeFrequency frequency, UUID department_id, UUID region_id, TicketStatus status) {
        List<Ticket> tickets = ticketService.getTickets();
        List<Ticket> filteredTickets = tickets.stream()
                .filter(ticket -> status.equals(ticket.getStatus())
                        && isDateInRange(ticket.getOpened_date_time(), startDate, endDate))
                .collect(Collectors.toList());

        if(department_id!=null && !department_id.equals("")) {
            filteredTickets = filteredTickets.stream()
                    .filter(ticket -> department_id.equals(ticket.getDepartment().getDepartment_id()))
                    .collect(Collectors.toList());
        }
        if(region_id!=null && !region_id.equals("")){
            filteredTickets = filteredTickets.stream()
                    .filter(ticket -> region_id.equals(ticket.getRegion().getRegion_id()))
                    .collect(Collectors.toList());
        }

        Map<String, Long> counts = new TreeMap<>(new StringDateComparator());

        if(frequency == RangeFrequency.DAILY){
            // Group and count tickets by day (ignoring time)
            Map<String, Long> dailyCounts = filteredTickets.stream()
                    .collect(Collectors.groupingBy(
                            ticket -> new SimpleDateFormat("dd/MM/yyyy").format(truncateTime(status==TicketStatus.OPEN ? ticket.getOpened_date_time():ticket.getClosing_date_time())),
                            Collectors.counting()
                    ));
            counts.putAll(dailyCounts);
        }
        if(frequency==RangeFrequency.MONTHLY){
            // Group and count tickets by month (MM/yyyy, ignoring time)
            Map<String, Long> monthlyCounts = filteredTickets.stream()
                    .collect(Collectors.groupingBy(
                            ticket -> new SimpleDateFormat("MM/yyyy").format(truncateTime(status==TicketStatus.OPEN ? ticket.getOpened_date_time():ticket.getClosing_date_time())),
                            Collectors.counting()
                    ));
            counts.putAll(monthlyCounts);
        }
        if(frequency == RangeFrequency.YEARLY){
            Map<String, Long> yearlyCounts = filteredTickets.stream()
                    .collect(Collectors.groupingBy(
                            ticket -> {
                                Calendar cal = Calendar.getInstance();
                                cal.setTime(truncateTime(status==TicketStatus.OPEN ? ticket.getOpened_date_time():ticket.getClosing_date_time()));
                                return Integer.toString(cal.get(Calendar.YEAR));
                            },
                            Collectors.counting()
                    ));
            counts.putAll(yearlyCounts);
        }
        return counts;
    }

    @Override
    public Map<String, Double> getAverageResolutionTime(Date startDate, Date endDate, RangeFrequency frequency, UUID department_id, UUID region_id) {
        List<Ticket> tickets = ticketService.getTickets();
        // filter tickets with Resolved status
        List<Ticket> filteredTickets = tickets.stream()
                .filter(ticket -> TicketStatus.RESOLVED.equals(ticket.getStatus())
                        && isDateInRange(ticket.getClosing_date_time(), startDate, endDate))
                .collect(Collectors.toList());
        if(department_id!=null && !department_id.equals("")) {
            filteredTickets = filteredTickets.stream()
                    .filter(ticket -> department_id.equals(ticket.getDepartment().getDepartment_id()))
                    .collect(Collectors.toList());
        }
        if(region_id!=null && !region_id.equals("")){
            filteredTickets = filteredTickets.stream()
                    .filter(ticket -> region_id.equals(ticket.getRegion().getRegion_id()))
                    .collect(Collectors.toList());
        }

        Map<String, Double> counts = new TreeMap<>(new StringDateComparator());

        if(frequency == RangeFrequency.DAILY){
            // Group and calculate average time to resolve by day (ignoring time just while grouping)
            Map<String, Double> dailyCounts = filteredTickets.stream()
                    .collect(Collectors.groupingBy(
                            ticket -> new SimpleDateFormat("dd/MM/yyyy").format(truncateTime(ticket.getClosing_date_time())),
                            Collectors.averagingLong((ticket)->(ticket.getClosing_date_time().getTime() - ticket.getOpened_date_time().getTime())/60000)
                    ));
            counts.putAll(dailyCounts);
        }
        if(frequency==RangeFrequency.MONTHLY){
            // Group and calculate average time to resolve by month (MM/yyyy, ignoring time)
            Map<String, Double> monthlyCounts = filteredTickets.stream()
                    .collect(Collectors.groupingBy(
                            ticket -> new SimpleDateFormat("MM/yyyy").format(truncateTime(ticket.getClosing_date_time())),
                            Collectors.averagingLong((ticket)->(ticket.getClosing_date_time().getTime() - ticket.getOpened_date_time().getTime())/60000)
                    ));
            counts.putAll(monthlyCounts);
        }
        if(frequency == RangeFrequency.YEARLY){
            Map<String, Double> yearlyCounts = filteredTickets.stream()
                    .collect(Collectors.groupingBy(
                            ticket -> {
                                Calendar cal = Calendar.getInstance();
                                cal.setTime(truncateTime(ticket.getClosing_date_time()));
                                return Integer.toString(cal.get(Calendar.YEAR));
                            },
                            Collectors.averagingLong((ticket)->(ticket.getClosing_date_time().getTime() - ticket.getOpened_date_time().getTime())/60000)
                    ));
            counts.putAll(yearlyCounts);
        }
        return counts;
    }

    public ResponseEntity<String> generateReport(MetricType metricType, Date startDate, Date endDate, RangeFrequency frequency, UUID department_id, UUID region_id, TicketStatus status) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(String.valueOf(metricType));
        Row headerRow = sheet.createRow(0);
        List<String> headers = new ArrayList<>();

        if(metricType == MetricType.TicketBasedOnStatus){
            Map<String, Long> tickets = getTicketsBasedOnStatus(startDate,endDate,frequency,department_id,region_id,status);
            headers.add("Date");
            headers.add("Tickets Based on Status");
            for(int col=0;col<headers.size();col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(headers.get(col));
            }
            int rowIdx = 1;
            for(String key:tickets.keySet()){
                Row row = sheet.createRow(rowIdx);
                row.createCell(0).setCellValue(key);
                row.createCell(1).setCellValue(tickets.get(key));
                rowIdx++;
            }
        } else if (metricType == MetricType.AverageUserRating) {
            Map<String, Double> tickets = getAverageResolutionTime(startDate,endDate,frequency,department_id,region_id);
            headers.add("Date");
            headers.add("Average Resolution Time");
            for(int col=0;col<headers.size();col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(headers.get(col));
            }
            int rowIdx = 1;
            for(String key:tickets.keySet()){
                Row row = sheet.createRow(rowIdx);
                row.createCell(0).setCellValue(key);
                row.createCell(1).setCellValue(tickets.get(key));
                rowIdx++;
            }
        }
        String folderpath = "C:\\Users\\vatsa\\Documents\\projects\\Spring_Boot_Projects\\GrievenceRedressal\\anlytics_service_generated_reports\\";
        Date now = new Date();
        String currDate = now.toString().replaceAll("[\\s:]", "");
        String filepath = folderpath+"Report_"+metricType.toString()+currDate+".xlsx";
        System.out.println(filepath);
        File file = new File(filepath);
        file.createNewFile();
        FileOutputStream out = new FileOutputStream(file);
        workbook.write(out);
        out.close();
        Report report = new Report();
        report.setMetricType(metricType);
        report.setGenerated_at(now);
        report.setReport_url(filepath);
        reportRepository.save(report);
        ResponseEntity<String> responseEntity = new ResponseEntity<>(filepath, HttpStatus.OK);
        return responseEntity;
    }
}
