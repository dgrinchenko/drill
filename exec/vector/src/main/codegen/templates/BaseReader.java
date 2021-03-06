/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
<@pp.dropOutputFile />
<@pp.changeOutputFile name="/org/apache/drill/exec/vector/complex/reader/BaseReader.java" />


<#include "/@includes/license.ftl" />

package org.apache.drill.exec.vector.complex.reader;

<#include "/@includes/vv_imports.ftl" />


/*
 * This class is generated using freemarker and the ${.template_name} template.
 */
@SuppressWarnings("unused")
public interface BaseReader extends Positionable{
  MajorType getType();
  MaterializedField getField();
  void reset();
  void read(UnionHolder holder);
  void read(int index, UnionHolder holder);
  void copyAsValue(UnionWriter writer);
  boolean isSet();
  void read(ValueHolder holder);

  public interface MapReader extends BaseReader, Iterable<String>{
    FieldReader reader(String name);
  }
  
  public interface RepeatedMapReader extends MapReader{
    boolean next();
    int size();
    void copyAsValue(MapWriter writer);
  }

  public interface DictReader extends RepeatedMapReader {
    void copyAsValue(DictWriter writer);

    /**
     * Obtain the index for given key in current row used to find a corresponding value with.
     * Used in generated code when retrieving value from Dict with
     * {@link org.apache.drill.common.expression.PathSegment.NameSegment}
     * in cases when {@link org.apache.drill.exec.vector.complex.DictVector#getValueType()} is complex.
     *
     * <p>Despite {@code key} is passed as {@code String} the value is converted to
     * actual type based on {@link org.apache.drill.exec.vector.complex.DictVector#getKeyType()}.
     *
     * @param key literal representing key value
     * @return index for the given key
     * @see org.apache.drill.exec.vector.complex.DictVector
     */
    int find(String key);

    /**
     * Obtain the index for given key in current row used to find a corresponding value with.
     * Used in generated code when retrieving value from Dict with
     * {@link org.apache.drill.common.expression.PathSegment.ArraySegment}
     * in cases when {@link org.apache.drill.exec.vector.complex.DictVector#getValueType()} is complex.
     *
     * <p>Despite {@code key} is passed as {@code int} the value is converted to
     * actual type based on {@link org.apache.drill.exec.vector.complex.DictVector#getKeyType()}.
     *
     * @param key literal representing key value
     * @return index for the given key
     * @see org.apache.drill.exec.vector.complex.DictVector
     */
    int find(int key);

    /**
     * Reads a value corresponding to a {@code key} into the {@code holder}.
     * If there is no entry in the row with the given {@code key}, value is set to null.
     *
     * <p>Used in generated code when retrieving value from Dict with
     * {@link org.apache.drill.common.expression.PathSegment.NameSegment}
     * in cases when {@link org.apache.drill.exec.vector.complex.DictVector#getValueType()} is primitive.
     *
     * <p>Despite {@code key} is passed as {@code String} the value is converted to
     * actual type based on {@link org.apache.drill.exec.vector.complex.DictVector#getKeyType()}.
     *
     * @param key literal representing key value
     * @param holder a holder to write value's value into
     * @see org.apache.drill.exec.vector.complex.DictVector
     */
    void read(String key, ValueHolder holder);

    /**
     * Reads a value corresponding to a {@code key} into the {@code holder}.
     * If there is no entry in the row with the given {@code key}, value is set to null.
     *
     * <p>Used in generated code when retrieving value from Dict with
     * {@link org.apache.drill.common.expression.PathSegment.ArraySegment}
     * in cases when {@link org.apache.drill.exec.vector.complex.DictVector#getValueType()} is primitive.
     *
     * <p>Despite {@code key} is passed as {@code int} the value is converted to
     * actual type based on {@link org.apache.drill.exec.vector.complex.DictVector#getKeyType()}.
     *
     * @param key literal representing key value
     * @param holder a holder to write value's value into
     * @see org.apache.drill.exec.vector.complex.DictVector
     */
    void read(int key, ValueHolder holder);
  }
  
  public interface ListReader extends BaseReader{
    FieldReader reader(); 
  }
  
  public interface RepeatedListReader extends ListReader{
    boolean next();
    int size();
    void copyAsValue(ListWriter writer);
  }
  
  public interface ScalarReader extends
  <#list vv.types as type><#list type.minor as minor><#assign name = minor.class?cap_first /> ${name}Reader, </#list></#list> 
  <#list vv.types as type><#list type.minor as minor><#assign name = minor.class?cap_first /> Repeated${name}Reader, </#list></#list>
  BaseReader {}
  
  interface ComplexReader{
    MapReader rootAsMap();
    ListReader rootAsList();
    boolean rootIsMap();
    boolean ok();
  }
}

