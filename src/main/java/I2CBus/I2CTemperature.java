package I2CBus;

import com.pi4j.wiringpi.Gpio;

public class I2CTemperature {

    private static final int MAXTIMINGS = 85;
    private int[] dht11_dat = {0,0,0,0};
    public String getTemperature() {
        int laststate = Gpio.HIGH;
        int j = 0;
        dht11_dat[0] = dht11_dat[1] = dht11_dat[2] = dht11_dat[3] = dht11_dat[4] = 0;
        StringBuilder value = new StringBuilder();

        Gpio.pinMode(3, Gpio.OUTPUT);
        Gpio.digitalWrite(3, Gpio.LOW);
        Gpio.delay(18);

        Gpio.digitalWrite(3, Gpio.HIGH);
        Gpio.pinMode(3, Gpio.INPUT);

        for (int i = 0; i < MAXTIMINGS; i++) {
            int counter = 0;
            while (Gpio.digitalRead(3) == laststate) {
                counter++;
                //i2c 프로토콜에 정해진 만큼 딜레이
                //수정 필요
                Gpio.delayMicroseconds(1);
                if (counter == 255) {
                    break;
                }
            }
            laststate = Gpio.digitalRead(3);
            if (counter == 255) {
                break;
            }

            //짝수이고 i가 4보다 크거나 같으면
            if ((i >= 4) && (i % 2 == 0)) {
                dht11_dat[j / 8] <<= 1;
                if (counter > 16) {
                    dht11_dat[j / 8] |= 1;
                }
                j++;
            }
        }
        //40비트의 데이터 (8bit*5) 가 잘 들어왔는지 체크
        if ((j >= 40) && checkParity()) {
            //습도데이터 [0] [1] 온도 [2] [3]
            float h = (float) ((dht11_dat[0] << 8) + dht11_dat[1]) / 10;
            if (h > 100) {
                h = dht11_dat[0];   // for DHT11
            }
            float c = (float) (((dht11_dat[2] & 0x7F) << 8) + dht11_dat[3]) / 10;
            if (c > 125) {
                c = dht11_dat[2];   // for DHT11
            }
            if ((dht11_dat[2] & 0x80) != 0) {
                c = -c;
            }
            float f = c * 1.8f + 32;
//            System.out.println( "Humidity = " + h + " Temperature = " + c + "(" + f + "f)");

            String humidity = Float.toString(h);
            String Temperature = Float.toString(c);

            String message = humidity + "/" + Temperature;
//            System.out.println("DHT11 Data : "+message);
            return message;
        } else {
            return "Data Load Failed";
        }

    }
    private boolean checkParity() {
        return (dht11_dat[4] == ((dht11_dat[0] + dht11_dat[1] + dht11_dat[2] + dht11_dat[3]) & 0xFF));
    }
}
