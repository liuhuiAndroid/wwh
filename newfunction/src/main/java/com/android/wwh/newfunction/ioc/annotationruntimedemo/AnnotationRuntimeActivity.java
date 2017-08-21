package com.android.wwh.newfunction.ioc.annotationruntimedemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lh on 2017/8/21.
 */

public class AnnotationRuntimeActivity extends AppCompatActivity {

    private static final String TAG = "AnnotationRuntimeActiviy";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println(TAG + " : " + createTable(Bean.class));
        // create table BeanTable(description text,field integer)

    }

    /**
     * 获取表名
     * 先判断Bean类是否有注解@Table，如果有则获取@Table对象并得到name方法的值
     *
     * @param bean
     * @return
     */
    private static String getTableName(Class<?> bean) {
        String name = null;
        //判断是否有Table注解
        if (bean.isAnnotationPresent(Table.class)) {
            //获取注解对象
            Table table = bean.getAnnotation(Table.class);
            name = table.name();
        }
        return name;
    }

    /**
     * 获取字段名与类型
     * 逐个分析Bean的成员变量是否有被@Column注解，有则获取其对应的字段名与类型
     *
     * @param bean
     * @return
     */
    private static List<NameAndType> getColumns(Class<?> bean) {
        List<NameAndType> columns = new ArrayList<NameAndType>();
        // getFields()获得某个类的所有的公共（public）的字段，包括父类。
        // getDeclaredFields()获得某个类的所有申明的字段，即包括public、private和proteced，但是不包括父类的申明字段。//
        Field[] fields = bean.getDeclaredFields();
        if (fields != null) {
            //分析Bean中的变量是否需要生成sql字段
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                if (field.isAnnotationPresent(Column.class)) {
                    //生成sql字段的名
                    Column column = field.getAnnotation(Column.class);
                    String name = column.name();
                    //生成sql字段的类型
                    String type = null;
                    // isAssignableFrom 必须是调用对象为参数的超类或者为超接口返回true.
                    if (int.class.isAssignableFrom(field.getType())) {
                        type = "integer";
                    } else if (String.class.isAssignableFrom(field.getType())) {
                        type = "text";
                    } else {
                        throw new RuntimeException("unspported type=" + field.getType().getSimpleName());
                    }
                    columns.add(new NameAndType(type, name));

                }

            }
        }
        return columns;
    }

    /**
     * 生成建表sql语句
     * 把前两部份获取的表名与字段结合起来组合成sql语句
     *
     * @param bean
     * @return
     */
    public static String createTable(Class<?> bean) {
        String tableName = getTableName(bean);
        List<NameAndType> columns = getColumns(bean);
        if (tableName != null && !tableName.equals("") && !columns.isEmpty()) {
            StringBuilder createTableSql = new StringBuilder("create table ");
            //加表名
            createTableSql.append(tableName);
            createTableSql.append("(");

            //加表中字段
            for (int i = 0; i < columns.size(); i++) {
                NameAndType column = columns.get(i);
                createTableSql.append(column.getName());
                createTableSql.append(" ");
                createTableSql.append(column.getType());
                // 追加下一个字段定义前需要添加逗号
                if (i != columns.size() - 1) {
                    createTableSql.append(",");
                }
            }
            createTableSql.append(")");
            return createTableSql.toString();
        }
        return null;
    }


}
