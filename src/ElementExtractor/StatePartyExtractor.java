package ElementExtractor;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class StatePartyExtractor {

    private HeadQuarterExtractor addressExtractor;

    public StatePartyExtractor() {
        this.addressExtractor = new HeadQuarterExtractor();
    }

    public void statePartyData(Element element) throws InterruptedException, IOException {

        if (element == null) {
            System.out.println("No table found for State Party");
            return;
        }

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("State Party Data");

        // Create header row
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Party Name");
        header.createCell(1).setCellValue("Election Symbol");
        header.createCell(2).setCellValue("Recognised In States");
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

                Elements symbolLink = cells.get(3).select("img");
                String electionSymbol = symbolLink.isEmpty() ? "Election Symbol not available" : "https:" + symbolLink.attr("src");

                String recognisedInStates = cells.get(6).text();
                String headQuarterAddress = addressExtractor.getHeadQuarterAddressData(addressLink);

                Row excelRow = sheet.createRow(rowIndex++);
                excelRow.createCell(0).setCellValue(partyName);
                excelRow.createCell(1).setCellValue(electionSymbol);
                excelRow.createCell(2).setCellValue(recognisedInStates);
                excelRow.createCell(3).setCellValue(headQuarterAddress);

                Thread.sleep(1000);
            }

            try (FileOutputStream fileOut = new FileOutputStream("StatePartyData.xlsx")) {
                workbook.write(fileOut);
            }

            workbook.close();
            System.out.println("Data successfully written to StatePartyData.xlsx");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
