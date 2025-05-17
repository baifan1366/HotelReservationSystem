/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelreservationsystem.model;

/**
 *
 * @author user
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Report implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int reportId;
    private String type;
    private String title;
    private String generationDate;
    private String content;
    private List<ReportItem> items;
    
    // Constructor with one parameter
    public Report(String type) {
        this.type = type;
        this.generationDate = new Date().toString();
        this.items = new ArrayList<>();
    }
    
    // Constructor with two parameters
    public Report(String title, String generationDate) {
        this.title = title;
        this.type = title;
        this.generationDate = generationDate;
        this.items = new ArrayList<>();
    }
    
    // Getters and setters
    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }

    public Date getGeneratedDate() {
        return new Date(); // For backward compatibility
    }

    public void setGeneratedDate(Date generatedDate) {
        this.generationDate = generatedDate.toString();
    }
    
    public String getGenerationDate() {
        return generationDate;
    }
    
    public void setGenerationDate(String generationDate) {
        this.generationDate = generationDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
    public void addReportItem(String label, String value) {
        items.add(new ReportItem(label, value));
    }
    
    public List<ReportItem> getItems() {
        return items;
    }
    
    @Override
    public String toString() {
        return "Report [reportId=" + reportId + ", type=" + type + 
               ", generationDate=" + generationDate + "]";
    }
    
    public static class ReportItem implements Serializable {
        private static final long serialVersionUID = 1L;
        
        private String label;
        private String value;
        
        public ReportItem(String label, String value) {
            this.label = label;
            this.value = value;
        }
        
        public String getLabel() {
            return label;
        }
        
        public String getValue() {
            return value;
        }
    }
}