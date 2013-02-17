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
package poc.tracexpenses.domain.bo.impl;

import java.util.List;

import poc.tracexpenses.domain.bo.CategoryBo;
import poc.tracexpenses.domain.bo.event.OnCategoriesLoadedEvent;
import poc.tracexpenses.domain.model.dao.CategoryDao;
import poc.tracexpenses.domain.model.entity.Category;
import poc.tracexpenses.util.aspects.Asynchronous;
import poc.tracexpenses.util.aspects.Transactional;

import com.google.inject.Inject;

public class CategoryBoImpl implements CategoryBo {

    @Inject
    private CategoryDao categoryDao;

    @Override
    @Asynchronous(post = OnCategoriesLoadedEvent.class)
    @Transactional
    public List<Category> getCategories() {
	return categoryDao.getCategories();
    }

}
