
package soft.edu.common;

import java.util.List;

/**
 * 
 * @Description: 分页显示实现
 * @author wsq
 * @since 2015-12-14
 * 
 */
@SuppressWarnings("rawtypes")
public class PageBean {

	private Integer pageSize = 20;
	/**
	 * 通过数据库分页查询出来的list集合
	 */

	private List list;
	/**
	 * 总记录数
	 */
	private Integer allRows = 0;
	/**
	 * 总页数
	 */
	private Integer totalPage = 0;
	/**
	 * 当前页
	 */
	private Integer currentPage = 1;

	public PageBean() {

	}

	public PageBean(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public Integer getAllRows() {
		return allRows;
	}

	public void setAllRows(Integer allRows) {
		this.allRows = allRows;
	}

	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	/**
	 * 得到当前页， 如果为0 则开始第一页，否则为当前页
	 * 
	 * @param currentPage
	 */
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = (currentPage == 0) ? 1 : currentPage;

	}

	/**
	 * 得到总页数
	 * 
	 * @param pageSize
	 *            每页记录数
	 * @param allRows
	 *            总记录数
	 * @return 总页数
	 */
	public Integer getTotalPages(Integer allRows) {
		Integer totalPage = (allRows % pageSize == 0) ? (allRows / pageSize) : (allRows / pageSize) + 1;

		return totalPage;
	}

	/**
	 * 得到当前开始记录号
	 * 
	 * @param pageSize
	 *            每页记录数
	 * @param currentPage
	 *            当前页
	 * @return
	 */
	public Integer getCurrentPageOffset() {
		Integer offset = pageSize * (currentPage - 1);

		return offset;
	}

}
