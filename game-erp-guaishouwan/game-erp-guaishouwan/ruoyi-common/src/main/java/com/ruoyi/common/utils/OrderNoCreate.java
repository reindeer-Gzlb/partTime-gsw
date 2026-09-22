package com.ruoyi.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class OrderNoCreate {

      public static String randomOrderCode() {
            SimpleDateFormat dmDate = new SimpleDateFormat("yyyyMMddHHmmss");
            String randata = getRandom(6);
            Date date = new Date();
            String dateran = dmDate.format(date);
            String Xsode = "XS" + dateran + randata;
            if (Xsode.length() < 24) {
                  Xsode = Xsode + 0;
            }
            return Xsode;
      }

      public static String getRandom(int len) {
            Random r = new Random();
            StringBuilder rs = new StringBuilder();
            for (int i = 0; i < len; i++) {
                  rs.append(r.nextInt(10));
            }
            return rs.toString();
      }
}
