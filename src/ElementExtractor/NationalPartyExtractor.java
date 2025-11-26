package ElementExtractor;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class NationalPartyExtractor {
    
    private HeadQuarterExtractor addressExtractor;
    
    public NationalPartyExtractor() {
        this.addressExtractor = new HeadQuarterExtractor();
    }

    public void nationalPartyData(Element element) throws IOException, InterruptedException {

        if (element == null) {
            System.out.println("No table found for National Party");
            return;
        }

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("National Party Data");

        // Create header row
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Party Name");
        header.createCell(1).setCellValue("Election Symbol");
        header.createCell(2).setCellValue("Political Position");
        header.createCell(3).setCellValue("Headquarter Address");

        Elements rows = element.select("tbody > tr");
        int rowIndex = 1;

        try {
            for (Element row : rows) {
                Elements cells = row.select("td");
                if (cells.size() < 6) continue;

                Elements link = cells.get(1).select("a");
                String addressLink = (link.isEmpty()) ? "" : "https://en.wikipedia.org/" + link.attr("href");

                String partyName = cells.get(1).text();

                Elements symbolLink = cells.get(4).select("img");
                String electionSymbol = symbolLink.isEmpty() ? "Election Symbol not available" : "https:" + symbolLink.attr("src");

                String partyPoliticalPosition = cells.get(5).text();
                String headQuarterAddress = addressExtractor.getHeadQuarterAddressData(addressLink);

                Row excelRow = sheet.createRow(rowIndex++);
                excelRow.createCell(0).setCellValue(partyName);
                excelRow.createCell(1).setCellValue(electionSymbol);
                excelRow.createCell(2).setCellValue(partyPoliticalPosition);
                excelRow.createCell(3).setCellValue(headQuarterAddress);

                Thread.sleep(1000);
            }

            try (FileOutputStream fileOut = new FileOutputStream("NationalPartyData.xlsx")) {
                workbook.write(fileOut);
            }

            workbook.close();
            System.out.println("Data successfully written to NationalPartyData.xlsx");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
