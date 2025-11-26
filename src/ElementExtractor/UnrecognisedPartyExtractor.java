package ElementExtractor;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class UnrecognisedPartyExtractor {

    private HeadQuarterExtractor addressExtractor;

    public UnrecognisedPartyExtractor() {
        this.addressExtractor = new HeadQuarterExtractor();
    }

    public void unRecognisedPartyData(Element element) throws IOException, InterruptedException {

        if (element == null) {
            System.out.println("No table found for Unrecognised Party");
            return;
        }

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Unrecognised Party Data");

        // Create header row
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Party Name");
        header.createCell(1).setCellValue("Election Symbol");
        header.createCell(2).setCellValue("States");
        header.createCell(3).setCellValue("Headquarter Address");

        Elements rows = element.select("tbody > tr");
        int rowIndex = 1;

        try {
            for (Element row : rows) {

                Elements cells = row.select("td");
                if (cells.size() < 2) continue;

                Elements link = cells.get(0).select("a");
                String addressLink = (link.isEmpty()) ? "" : "https://en.wikipedia.org/" + link.attr("href");

                String partyName = cells.get(0).text();
                String electionSymbol = "Election Symbol not available";
                String states = cells.get(3).text();
                String headQuarterAddress = addressExtractor.getHeadQuarterAddressData(addressLink);

                Row excelRow = sheet.createRow(rowIndex++);
                excelRow.createCell(0).setCellValue(partyName);
                excelRow.createCell(1).setCellValue(electionSymbol);
                excelRow.createCell(2).setCellValue(states);
                excelRow.createCell(3).setCellValue(headQuarterAddress);

                Thread.sleep(1000);
            }

            try (FileOutputStream fileOut = new FileOutputStream("UnrecognisedPartyData.xlsx")) {
                workbook.write(fileOut);
            }

            workbook.close();
            System.out.println("Data successfully written to UnrecognisedPartyData.xlsx");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
