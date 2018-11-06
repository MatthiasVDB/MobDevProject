package be.pxl.kartingapp.utilities;

import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class Converters {
     public static float convertLaptimeStringToMilliseconds(String lapTime){
         //int multiplier[] = {3600000, 60000, 100};
         int multiplier[] = {60000, 1000, 1};
         char[] splitters = {':','.'};
         //String splits[] = lapTime.split(":");
         String splits[] = lapTime.split(":|\\.");
         float time = 0;
         for (int x = 0; x < splits.length; x++) {
             time += (Integer.parseInt(splits[x]) * multiplier[x]);
         }
         return time;
     }

     public static String convertMillisecondsToLaptimeString(float milliseconds){
         /*return String.format("00:%02d:%02d",
                 TimeUnit.MILLISECONDS.toMinutes((long)milliseconds),
                 TimeUnit.MILLISECONDS.toSeconds((long)milliseconds) -
                         TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)milliseconds))
         );*/

         int minutes = (int)(milliseconds /(1000 * 60));
         int seconds = (int)(milliseconds / 1000 % 60);
         int millis  = (int)(milliseconds % 1000);

         //return String.format("%02d:%02d:%02d", minutes, seconds, millis);
         return String.format("%02d:%02d.%03d", minutes, seconds, millis);

     }
}
