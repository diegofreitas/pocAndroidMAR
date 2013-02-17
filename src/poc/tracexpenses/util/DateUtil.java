/*
 * Copyright 2013 Diego Lins de Freitas
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 */
package poc.tracexpenses.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

    public static String callendarToString(Calendar createdAt) {
	String createdAtText = null;
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

	createdAtText = dateFormat.format(createdAt.getTime());

	return createdAtText;
    }

    public static Calendar stringToCallendar(String string) {
	Calendar calendar = Calendar.getInstance();
	try {
	    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",
		    Locale.US);
	    Date parsed = dateFormat.parse(string);

	    calendar.setTime(parsed);
	} catch (ParseException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return calendar;
    }

}
