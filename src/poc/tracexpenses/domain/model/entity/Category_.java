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
package poc.tracexpenses.domain.model.entity;

import android.provider.BaseColumns;

public class Category_ {

	public static final String TABLE = "categories"; //

	public static final String ID = BaseColumns._ID;
	public static final String FULL_ID = TABLE +"."+BaseColumns._ID;
	public static final String NAME = "name";
	public static final String TYPE = "type";
}
