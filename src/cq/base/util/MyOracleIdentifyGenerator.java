package cq.base.util;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.exception.JDBCExceptionHelper;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.id.PersistentIdentifierGenerator;

public class MyOracleIdentifyGenerator implements IdentifierGenerator, Configurable {

    private long next;

    private String sql;				//查询条件
    private String table;			//表
    private String column;			//列 默认主键
    private String schema;			//
    private String businessType="";	//业务类型
    private int serialNumLength=4;	//流水号长度
    private int businessNumLength;	//业务号长度
    private int dateNumLength;		//日期号长度

    
    public synchronized Serializable generate(SessionImplementor session,
            Object object) throws HibernateException {
        SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
        String preDate = f.format(new Date());
        dateNumLength=preDate.length();
        businessNumLength = businessType.length();
//        return businessType + preDate + getNext(session, businessType,table);
        return preDate + getNext(session, businessType,table)+businessType;

    }

    public void configure(org.hibernate.type.Type type, Properties params,
            Dialect d) throws MappingException {
        table = params.getProperty("table");
        if (table == null)
            table = params.getProperty(PersistentIdentifierGenerator.TABLE);
        column = params.getProperty("column");
        if (column == null)
            column = params.getProperty(PersistentIdentifierGenerator.PK);
        schema = params.getProperty(PersistentIdentifierGenerator.SCHEMA);
        businessType = params.getProperty("businessType");
        if(businessType==null)
        	businessType="";

    }
    /**
     * 得到当前表ID的最后六位的最大数
     * 
     * @param session
     * @param jsbh
     * @return
     */
    private String getNext(SessionImplementor session, String bh,String table) {
    	//oracle
//        sql = "select  max(substr("+column+","+(businessNumLength+dateNumLength)+")) from "+(schema == null ? table : schema + '.' + table)+" where substr("+column+","+businessNumLength+","+dateNumLength+") = to_char(sysdate,'yyMMdd') and substr("+column+",0,"+businessNumLength+") = '" + bh + "' and  length("+column+")="+(businessNumLength+dateNumLength+serialNumLength)+" ";
    	//sqlserver 老版本
//      sql=" SELECT MAX(SUBSTRING("+column+", "+(businessNumLength+dateNumLength)+","+serialNumLength+"))" +
//		" FROM "+table+" " +
//		" WHERE (SUBSTRING("+column+", "+businessNumLength+", "+dateNumLength+") = substring(convert(char, getdate(), 112),3,6)) AND (SUBSTRING("+column+", 0, "+businessNumLength+") = '"+bh+"')";
//    	sql=" SELECT MAX(SUBSTRING("+column+", "+(dateNumLength+1)+","+serialNumLength+"))" +
//		" FROM "+table+" " +
//		" WHERE (SUBSTRING("+column+", "+1+", "+dateNumLength+") = convert(char, getdate(), 112)) AND (SUBSTRING("+column+", "+(dateNumLength+serialNumLength+1)+", "+businessNumLength+") = '"+bh+"')";
//    	
    	
    	sql=" SELECT MAX(SUBSTR("+column+", "+(dateNumLength+1)+","+serialNumLength+"))" +
		" FROM "+table+" " +
		" WHERE (SUBSTR("+column+", "+1+", "+dateNumLength+") = to_char( sysdate, 'yyyyMMdd')) AND (SUBSTR("+column+", "+(dateNumLength+serialNumLength+1)+", "+businessNumLength+") = '"+bh+"')";
        
    	try {
            PreparedStatement st = session
                    .getBatcher()
                    .prepareSelectStatement(
                            sql);
            try {
                ResultSet rs = st.executeQuery();
                try {
                    if (rs.next()) {
                        next = rs.getLong(1) + 1;
                        if (rs.wasNull())
                            next = 1;
                    } else {
                        next = 1;
                    }
                    sql = null;
                } finally {
                    rs.close();
                }
            } finally {
                session.getBatcher().closeStatement(st);
            }
            return toString(serialNumLength, next);
        } catch (SQLException sqle) {
            throw JDBCExceptionHelper.convert(session.getFactory()
                    .getSQLExceptionConverter(), sqle,
                    "获取主键失败",
                    sql);
        }
    }

    /**
     * 格式化数字不足补齐
     * 
     * @param num
     * @param value
     * @return
     */
    public static String toString(int num, long value) {
        String result = (new Long(value)).toString();
        while (num > result.length()) {
            result = "0" + result;
        }
        return result;
    }
}
