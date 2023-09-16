package cs211.project.services;

import java.util.Map;

public interface Datasource<T> {
    /**
     * อ่านข้อมูลจาก datasource
     * @return ข้อมูลที่อ่านได้ซึ่งจะเป็น object ของ T (T คือ generic type ที่เรากำหนดให้กับ interface นี้)
     */
    T readData();
    /**
     * เขียนข้อมูลลง datasource
     * @param data ข้อมูลที่จะเขียนลง csv ซึ่ง data type ของ parameter จะเป็น T (T คือ generic type ที่เรากำหนดให้กับ interface นี้)
     */
    void writeData(T data);

    /**
     * ลบข้อมูลที่มี id ใน csv ตรงกับ parameter ที่รับเข้ามา ออกจาก datasource
     * @param id id ของข้อมูลที่จะลบ
     */
    void deleteById(String id);

    /**
     * ลบข้อมูลทั้งหมดที่มี column ตรงกับ value ที่รับเข้ามา ออกจาก datasource เช่น ลบข้อมูลทั้งหมดที่มีค่าใน column ชื่อ age เป็น 18
     * @param value ค่าที่จะใช้ในการเปรียบเทียบกับค่าใน column
     * @param column ชื่อ column ที่จะใช้ในการเปรียบเทียบ
     */
    void deleteAllByColumnAndValue(String column, String value);

    /**
     * ลบข้อมูลแบบมีเงื่อนไขเช่น ลบข้อมูลทั้งหมดที่มีค่าใน column ชื่อ age เป็น 18 และ column ชื่อ name เป็น "John"
     * @param conditions เงื่อนไขที่จะใช้ในการลบข้อมูล โดยใช้ Map โดย key จะเป็นชื่อ column และ value จะเป็นค่าที่จะใช้ในการเปรียบเทียบกับค่าใน column
     */
    void deleteByConditions(Map<String, String> conditions);

    /**
     * อัพเดทค่าใน column ของข้อมูลที่มี id ตรงกับ parameter ที่รับเข้ามา
     * @param id id ของข้อมูลที่จะอัพเดท
     * @param targetColumn column ที่จะอัพเดทค่า
     * @param newValue ค่าใหม่ที่จะใส่ลงไปใน column ที่ต้องการอัพเดท
     */
    void updateColumnById(String id, String targetColumn, String newValue);

    /**
     * อัพเดทค่าใน column ของข้อมูลที่มี id ตรงกับ parameter ที่รับเข้ามาซึ่งจะอัพเดทค่าได้หลาย column ในครั้งเดียว
     * @param id id ของข้อมูลที่จะอัพเดท
     * @param newValues จะเป็น Map โดย key จะเป็นชื่อ column และ value จะเป็นค่าใหม่ที่จะใส่ลงไปใน column ที่ต้องการอัพเดท
     */
    void updateColumnsById(String id, Map<String, String> newValues);

    T findById(String id);

    T findAllByColumnsAndValue(Map<String, String> conditions);

    T query(String query);

}
