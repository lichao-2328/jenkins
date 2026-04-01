package Util;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.itextpdf.io.exceptions.IOException;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.font.PdfFontFactory.EmbeddingStrategy;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

import entity.Student;

public class StudentPdfExporter {
	 private final List<Student> students;

	    public StudentPdfExporter(List<Student> students) {
	        this.students = students;
	    }

	    public void export(HttpServletResponse response) throws IOException, java.io.IOException {
	        // 注意：不要关闭 response.getOutputStream()（容器负责），但要关闭 Document（会刷新流）
	        PdfWriter writer = new PdfWriter(response.getOutputStream());
	        PdfDocument pdf = new PdfDocument(writer);
	        Document document = new Document(pdf);

	        // 字体（按需改路径或用资源）
	        String fontPath = "C:/Windows/Fonts/simsun.ttc,0";
	        PdfFont font = PdfFontFactory.createFont(fontPath, PdfEncodings.IDENTITY_H, EmbeddingStrategy.PREFER_EMBEDDED, true);
	        // 标题
	        document.add(new Paragraph("学生资料列表").setFont(font).setBold().setFontSize(18).setTextAlignment(TextAlignment.CENTER));
	        document.add(new Paragraph("\n"));

	        Table table = new Table(UnitValue.createPercentArray(new float[]{6, 20, 20, 10, 10,10, 20})).useAllAvailableWidth();
	        String[] headers = {"ID", "学号", "姓名", "性别", "年龄", "班级","入学日期"};
	        for (String h : headers) {
	            table.addHeaderCell(new Cell().add(new Paragraph(h).setFont(font).setBold()).setTextAlignment(TextAlignment.CENTER));
	        }

	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        for (Student s : students) {
	            table.addCell(new Cell().add(new Paragraph(String.valueOf(s.getId())).setFont(font)));
	            table.addCell(new Cell().add(new Paragraph(nvl(s.getStudentNo())).setFont(font)));
	            table.addCell(new Cell().add(new Paragraph(nvl(s.getName())).setFont(font)));
	            table.addCell(new Cell().add(new Paragraph(nvl(s.getGender())).setFont(font)));
	            table.addCell(new Cell().add(new Paragraph(String.valueOf(s.getAge())).setFont(font)));
	            table.addCell(new Cell().add(new Paragraph(nvl(s.getClassId())).setFont(font)));
	            table.addCell(new Cell().add(new Paragraph(s.getEnroll_date() != null ? sdf.format(s.getEnroll_date()) : "-").setFont(font)));
	        }

	        document.add(table);
	        document.close(); // 重要：会 flush 并释放 iText 资源
	    }

	    private String nvl(String v) { return v == null || v.isEmpty() ? "-" : v; }
	}