package soft.edu.common;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class BaseDao<T> {
	public boolean login(String _id, String _pwd,String querySql) {
		// TODO Auto-generated method stub
		Connection conn = DatabaseConnection.getConnection();
		boolean b=false;
		try{
		PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(querySql);
		pstmt.setString(1, _id);
		pstmt.setString(2, _pwd);
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()){
			b=true;
		}
		DatabaseConnection.close(rs);
		DatabaseConnection.close(pstmt);
		DatabaseConnection.close(conn);
		}catch (SQLException e){
			System.out.println("数据异常");
			e.printStackTrace();
		}
		return b;
	}
	/**
	 * 
	 * @Description: 根据id查询单条记录
	 * @param @param id
	 * @param @param qureySql
	 * @param @param t
	 * @param @return   
	 * @return T  
	 * @throws
	 * @author wangsuqi
	 * @date 2016年9月3日
	 */
	public T queryItemId(String id,String querySql,T t) {
		// TODO Auto-generated method stub
		Connection conn = DatabaseConnection.getConnection();
		Class<? extends Object> ct=t.getClass();
		try{
		PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(querySql);
		pstmt.setString(1, id);
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()){
			Method[] allMethods=ct.getDeclaredMethods();
			List<Method> setMethods=new ArrayList<>();
			for(Method m:allMethods){
				if(m.getReturnType().getName()=="void"){
					setMethods.add(m);
				}
			}
			for(int i=0;i<setMethods.size();i++){
				try {
					setMethods.get(i).invoke(t,rs.getObject(setMethods.get(i).getName().split("set")[1].toLowerCase()));
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		DatabaseConnection.close(rs);
		DatabaseConnection.close(pstmt);
		DatabaseConnection.close(conn);
		}catch (SQLException e){
			System.out.println("数据异常");
			e.printStackTrace();
		}
		return t;
	}
	/**
	 * 
	 * @Description: 根据外键查询多条记录，用于级联查询
	 * @param @param id
	 * @param @param qureySql
	 * @param @param cla
	 * @param @return
	 * @param @throws NoSuchMethodException
	 * @param @throws SecurityException
	 * @param @throws InstantiationException
	 * @param @throws IllegalAccessException
	 * @param @throws IllegalArgumentException
	 * @param @throws InvocationTargetException   
	 * @return List<BaseEntity>  
	 * @throws
	 * @author wangsuqi
	 * @date 2016年9月3日
	 */
	public List<BaseEntity> queryItemsId(String id,String querySql,Class<?> cla) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		// TODO Auto-generated method stub
		System.out.println(querySql);
		Connection conn = DatabaseConnection.getConnection();
		List<BaseEntity> objs=new ArrayList<>();
		Constructor<?> construtor=cla.getConstructor();
//		System.out.println(ct);
		try{
		PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(querySql);
		pstmt.setString(1, id);
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()){
			BaseEntity entity=(BaseEntity)construtor.newInstance();
			Method[] allMethods=cla.getDeclaredMethods();
			List<Method> setMethods=new ArrayList<>();
			for(Method m:allMethods){
				if(m.getReturnType().getName()=="void"){
					setMethods.add(m);
				}
			}
//			System.out.println("setter count:"+setMethods.size());
			for(int i=0;i<setMethods.size();i++){
				try {
//					System.out.println(setMethods.get(i).getName()+" "+setMethods.get(i).getName().split("set")[1].toLowerCase());
					setMethods.get(i).invoke(entity,rs.getObject(setMethods.get(i).getName().split("set")[1].toLowerCase()));
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			objs.add(entity);
		}
		DatabaseConnection.close(rs);
		DatabaseConnection.close(pstmt);
		DatabaseConnection.close(conn);
		}catch (SQLException e){
			System.out.println("数据异常");
			e.printStackTrace();
		}
		return objs;
	}
	public PageBean queryItemsToPage(PageBean pagebean,Class<T> ct,String querySql,String...orderItems) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException, InstantiationException{
		Connection conn = DatabaseConnection.getConnection();
		Constructor<T> constor=ct.getConstructor();
		Method[] allMethods=ct.getDeclaredMethods();
		/*
		 * 获取所有get方法
		 */
		List<Method> setMethods=new ArrayList<>();
		for(Method m:allMethods){
			if(m.getReturnType().getName()=="void"){
				setMethods.add(m);
			}
		}
		/*
		 * 添加分页查询语句
		 * querySql="("+querySql;
		 * querySql+=(" LIMIT "+pagebean.getCurrentPageOffset().intValue()+","+pagebean.getPageSize().intValue()+") ");
		 * 
		 */
		/*
		 * 添加排序语句
		 */
		if(orderItems.length>0){
			querySql+=" ORDER BY ";
			String orderSql="";
			for(String orderItem:orderItems){
				orderSql+=(orderItem+" ");
			}
			querySql+=orderSql.trim().replaceAll("\\s*", ",");
		}
		System.out.println(querySql);
		/*
		 *预处理对象执行查询语句生成结果集对象 
		 */
		PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(querySql);
		ResultSet rs = pstmt.executeQuery();
		/*
		 * 设置分页
		 */
		rs.setFetchDirection(pagebean.getCurrentPageOffset().intValue());
		rs.setFetchSize(pagebean.getPageSize().intValue());
		List<T> list=new ArrayList<>();
		while(rs.next()){
			T t=(T) constor.newInstance();
			/*
			 * 为对象添加属性值
			 */
			for(int i=0;i<setMethods.size();i++){			
					setMethods.get(i).invoke(t,rs.getObject(setMethods.get(i).getName().split("set")[1].toLowerCase()));		
				}
			list.add(t);
			}
		/*
		 * 生成分页对象
		 */
		pagebean.setList(list);
		int allRows=rs.getRow();
		System.out.println(allRows);
		pagebean.setAllRows(allRows);
		int totalPage=pagebean.getTotalPages(allRows);
		pagebean.setTotalPage(totalPage);
		return pagebean;
	}
	public int updatePwd(String querySql,String updateSql){
		Connection conn = DatabaseConnection.getConnection();
		int returnVal = -1;
		try{
			Statement stmt = (Statement) conn.createStatement();
			ResultSet rs = stmt.executeQuery(querySql);
			if(rs.next()){
				returnVal = stmt.executeUpdate(updateSql);
			}
			DatabaseConnection.close(rs);
			DatabaseConnection.close(stmt);
		}catch(SQLException e){
			System.err.println("数据异常");
			e.printStackTrace();
		}
		return returnVal;
	}
	public int update(String updateSql){
		System.out.println(updateSql);
		Connection conn = DatabaseConnection.getConnection();
		int returnVal = -1;
		try{
			Statement stmt = (Statement) conn.createStatement();
			returnVal = stmt.executeUpdate(updateSql);
			DatabaseConnection.close(stmt);
			DatabaseConnection.close(conn);
		}catch(SQLException e){
			System.err.println("数据异常");
			e.printStackTrace();
		}
		System.out.println("accomplish");
		return returnVal;
	}
	public void del(String delSql){
		System.out.println(delSql);
		Connection conn = DatabaseConnection.getConnection();
		try{
			Statement stmt = (Statement) conn.createStatement();
			stmt.execute(delSql);
			DatabaseConnection.close(stmt);
			DatabaseConnection.close(conn);
		}catch(SQLException e){
			System.err.println("数据异常");
			e.printStackTrace();
		}
		System.out.println("accomplish");
	}
	/**
	 * 
	 * @Description: 添加操作
	 * @param @param t 实体
	 * @param @param schemaName 表名
	 * @param @throws IllegalAccessException
	 * @param @throws IllegalArgumentException
	 * @param @throws InvocationTargetException   
	 * @return void  
	 * @throws
	 * @author wangsuqi
	 * @date 2016年9月3日
	 */
	public void insert(T t,String schemaName) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		Connection conn = DatabaseConnection.getConnection();
		Class<? extends Object> ct=t.getClass();
		List<Method> getMethods=new ArrayList<>();
		Method[] methods=ct.getDeclaredMethods();
		StringBuffer sb=new StringBuffer("INSERT INTO "+schemaName+"(");
		for(Method m:methods){
			if(!m.getReturnType().getName().equals("void")){
				getMethods.add(m);
			}
		}
		/**
		 * 通过getter获取键
		 */
		for(int i=0;i<getMethods.size();i++){
			if(i==0){
				sb.append("`"+getMethods.get(i).getName().trim().split("get")[1].toLowerCase()+"`");
			}
			else{
				sb.append(",`"+getMethods.get(i).getName().trim().split("get")[1].toLowerCase()+"`");
			}
		}
		/**
		 * 通过反射调取getter,获取值
		 */
		sb.append(") VALUES (");
		for(int i=0;i<getMethods.size();i++){
			if(i==0){
				sb.append("'"+getMethods.get(i).invoke(t).toString().trim()+"'");
			}
			else{
				sb.append(",'"+getMethods.get(i).invoke(t).toString().trim()+"'");
			}
		}
		sb.append(")");
		System.out.println(sb.toString());
		try{
			Statement stmt = (Statement) conn.createStatement();
			stmt.execute(sb.toString());
			DatabaseConnection.close(stmt);
			DatabaseConnection.close(conn);
		}catch (SQLException e){
			System.out.println("数据异常");
			e.printStackTrace();
		}
	}
	public int updatePhoto(String updateSql){
		Connection conn = DatabaseConnection.getConnection();
		int returnVal = -1;
		System.out.println(updateSql);
		try{
			Statement stmt = (Statement) conn.createStatement();
			returnVal = stmt.executeUpdate(updateSql);
			DatabaseConnection.close(stmt);
			DatabaseConnection.close(conn);
		}catch(SQLException e){
			System.err.println("数据异常");
			e.printStackTrace();
		}
		return returnVal;
	}
}
