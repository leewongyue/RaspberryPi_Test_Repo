package I2CBus;


import com.pi4j.component.lcd.LCD;
import com.pi4j.component.lcd.LCDTextAlignment;
import com.pi4j.component.lcd.impl.I2CLcdDisplay;

public class I2cDisplay2 {
    public static final int LCD_ROW_1 = 0;
    public static final int LCD_ROW_2 = 1;

    public static final String DEMO_TITLE = "Temp";
    static byte[] char1 = { (byte)00100,(byte)01010,(byte)01010,(byte)00100,(byte)11111,(byte)00000,(byte)01000,(byte)01110}; //온
    static byte[] char2 = {(byte)00000,(byte)01110,(byte)01000,(byte)01110,(byte)00000,(byte)00100,(byte)01110,(byte)01110};//도

    static byte[] char3 = {(byte)00100,(byte)01010,(byte)00000,(byte)01010,(byte)01110,(byte)01010,(byte)01110,(byte)00000}; //습
    static byte[] char4 = {(byte)00000,(byte)01110,(byte)01000,(byte)01110,(byte)00000,(byte)00100,(byte)01110,(byte)00000}; //도

    public static void main(String[] args) throws Exception {

        // for 1602 LCD + PCF8574 I2C module
        I2CLcdDisplay lcd = new I2CLcdDisplay(2, 16, 1, 0x27, 3, 0, 1, 2, 7, 6, 5, 4);

        lcd.write(LCD_ROW_1, DEMO_TITLE,LCDTextAlignment.ALIGN_CENTER);
        Thread.sleep(3000);

        lcd.clear();
        Thread.sleep(1000);
//
//        // show all characters
////        for (int ch = 0x10; ch <= 0xFF; ch += 4) {
////            lcd.write(LCD_ROW_1, "%02XH %02XH %02XH %02XH", ch, ch + 1, ch + 2, ch + 3);
////            lcd.write(LCD_ROW_2, " %c   %c   %c   %c",      ch, ch + 1, ch + 2, ch + 3);
////            Thread.sleep(800);
////        }
//
//        // show characters at specified positions
//        lcd.setCursorHome();
//
//        for (int column = 0; column < lcd.getColumnCount(); column++) {
//            lcd.write((byte)0xFF);
//            Thread.sleep(100);
//        }
//        for (int column = lcd.getColumnCount() - 1; column >= 0; column--) {
//            lcd.write(LCD_ROW_2, column, (byte)0xFF);
//            Thread.sleep(100);
//        }
//
//        lcd.setCursorHome();
//        for (int column = 0; column < lcd.getColumnCount(); column++) {
//            lcd.write(DEMO_TITLE.charAt(column));
//            Thread.sleep(100);
//        }
//
//        for (int column = lcd.getColumnCount() - 1; column >= 0; column--) {
//            lcd.write(LCD_ROW_2, column, ' ');
//            Thread.sleep(100);
//        }
//
//        // show text alignment
//        lcd.writeln(LCD_ROW_2, "< LEFT", LCDTextAlignment.ALIGN_LEFT);
//        Thread.sleep(2000);
//
//        lcd.writeln(LCD_ROW_2, "RIGHT >", LCDTextAlignment.ALIGN_RIGHT);
//        Thread.sleep(2000);
//
//        lcd.writeln(LCD_ROW_2, "< CENTER >", LCDTextAlignment.ALIGN_CENTER);
//        Thread.sleep(2000);
//        lcd.write(LCD_ROW_1,char1);
//        lcd.write(LCD_ROW_1,char2);
//        lcd.write(LCD_ROW_1,char3);
//        lcd.write(LCD_ROW_1,char4);
//        Thread.sleep(4000);

        // show clock
        SimpleDateFormat formatter1 = new SimpleDateFormat("HH:mm:ss");
        lcd.write(LCD_ROW_1, DEMO_TITLE);
        lcd.writeln(LCD_ROW_2, formatter1.format(new Date()), LCDTextAlignment.ALIGN_CENTER);
        Thread.sleep(4000);

        DHT11 dht = new DHT11();

//        for (int i=0; i<10; i++) {
//            Thread.sleep(2000);
//            dht.getTemperature();
//        }
//
//        System.out.println("Done!!");

        while (true) {
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");

            lcd.write(LCD_ROW_1,dht.getTemperature(),LCDTextAlignment.ALIGN_CENTER);
//            lcd.write(LCD_ROW_1, DEMO_TITLE);
            lcd.writeln(LCD_ROW_2, formatter.format(new Date()), LCDTextAlignment.ALIGN_CENTER);
            Thread.sleep(3000);
            lcd.clear();
            Thread.sleep(1000);

            //테스트
            try {
                I2CLcdDisplay lcd = new I2CLcdDisplay(2, 16, 1, 0x27, 3, 0, 1, 2, 7, 6, 5, 4);
                SimpleDateFormat date = new SimpleDateFormat("HH:mm:ss");

                while (true) {
                    lcd.write(LCD_ROW_1, DEMO_TITLE);
                    Thread.sleep(3000);
                    lcd.writeln(LCD_ROW_2, date.format(new Date()), LCDTextAlignment.ALIGN_CENTER);
                    Thread.sleep(3000);
                    lcd.clear();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("exception message : "+e.toString());
            } catch (Exception e) {
                System.out.println("exception message : "+e.toString());
                e.printStackTrace();
            }



        }
    }
}
