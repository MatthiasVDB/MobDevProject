package be.pxl.kartingapp.utilities;

import java.util.concurrent.TimeUnit;

public class Converters {
     public static float convertLaptimeStringToMilliseconds(String lapTime){
         int multiplier[] = {3600000, 60000, 100};
         String splits[] = lapTime.split(":");
         float time = 0;
         for (int x = 0; x < splits.length; x++) {
             time += (Integer.parseInt(splits[x]) * multiplier[x]);
         }
         return time;
     }

     public static String convertMillisecondsToLaptimeString(float milliseconds){
         return String.format("%02d min %02d sec",
                 TimeUnit.MILLISECONDS.toMinutes((long)milliseconds),
                 TimeUnit.MILLISECONDS.toSeconds((long)milliseconds) -
                         TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)milliseconds))
         );
     }
}
