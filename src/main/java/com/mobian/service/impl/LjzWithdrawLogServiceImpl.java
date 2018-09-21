package com.mobian.service.impl;

import com.mobian.absx.F;
import com.mobian.dao.LjzWithdrawLogDaoI;
import com.mobian.model.TljzWithdrawLog;
import com.mobian.pageModel.*;
import com.mobian.service.LjzBalanceLogServiceI;
import com.mobian.service.LjzUserServiceI;
import com.mobian.service.LjzWithdrawLogServiceI;
import com.mobian.thirdpart.wx.HttpUtil;
import com.mobian.thirdpart.wx.PayCommonUtil;
import com.mobian.thirdpart.wx.WeixinUtil;
import com.mobian.thirdpart.wx.XMLUtil;
import com.mobian.util.Constants;
import com.mobian.util.DateUtil;
import com.mobian.util.IpUtil;
import com.mobian.util.MyBeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

@Service
public class LjzWithdrawLogServiceImpl extends BaseServiceImpl<LjzWithdrawLog> implements LjzWithdrawLogServiceI {

	@Autowired
	private LjzWithdrawLogDaoI ljzWithdrawLogDao;

	@Autowired
	private LjzBalanceLogServiceI ljzBalanceLogService;

	@Autowired
	private LjzUserServiceI ljzUserService;

	@Override
	public DataGrid dataGrid(LjzWithdrawLog ljzWithdrawLog, PageHelper ph) {
		List<LjzWithdrawLog> ol = new ArrayList<LjzWithdrawLog>();
		String hql = " from TljzWithdrawLog t ";
		DataGrid dg = dataGridQuery(hql, ph, ljzWithdrawLog, ljzWithdrawLogDao);
		@SuppressWarnings("unchecked")
		List<TljzWithdrawLog> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TljzWithdrawLog t : l) {
				LjzWithdrawLog o = new LjzWithdrawLog();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(LjzWithdrawLog ljzWithdrawLog, Map<String, Object> params) {
		String whereHql = "";	
		if (ljzWithdrawLog != null) {
			whereHql += " where t.isdeleted = 0 ";
			if (!F.empty(ljzWithdrawLog.getIsdeleted())) {
				whereHql += " and t.isdeleted = :isdeleted";
				params.put("isdeleted", ljzWithdrawLog.getIsdeleted());
			}		
			if (!F.empty(ljzWithdrawLog.getWithdrawNo())) {
				whereHql += " and t.withdrawNo = :withdrawNo";
				params.put("withdrawNo", ljzWithdrawLog.getWithdrawNo());
			}		
			if (!F.empty(ljzWithdrawLog.getAmount())) {
				whereHql += " and t.amount = :amount";
				params.put("amount", ljzWithdrawLog.getAmount());
			}		
			if (!F.empty(ljzWithdrawLog.getServiceAmt())) {
				whereHql += " and t.serviceAmt = :serviceAmt";
				params.put("serviceAmt", ljzWithdrawLog.getServiceAmt());
			}		
			if (!F.empty(ljzWithdrawLog.getUserId())) {
				whereHql += " and t.userId = :userId";
				params.put("userId", ljzWithdrawLog.getUserId());
			}		
			if (!F.empty(ljzWithdrawLog.getRealName())) {
				whereHql += " and t.realName = :realName";
				params.put("realName", ljzWithdrawLog.getRealName());
			}		
			if (!F.empty(ljzWithdrawLog.getPhone())) {
				whereHql += " and t.phone = :phone";
				params.put("phone", ljzWithdrawLog.getPhone());
			}		
			if (!F.empty(ljzWithdrawLog.getContent())) {
				whereHql += " and t.content = :content";
				params.put("content", ljzWithdrawLog.getContent());
			}		
			if (!F.empty(ljzWithdrawLog.getHandleStatus())) {
				whereHql += " and t.handleStatus = :handleStatus";
				params.put("handleStatus", ljzWithdrawLog.getHandleStatus());
			}		
			if (!F.empty(ljzWithdrawLog.getHandleLoginId())) {
				whereHql += " and t.handleLoginId = :handleLoginId";
				params.put("handleLoginId", ljzWithdrawLog.getHandleLoginId());
			}		
			if (!F.empty(ljzWithdrawLog.getHandleRemark())) {
				whereHql += " and t.handleRemark = :handleRemark";
				params.put("handleRemark", ljzWithdrawLog.getHandleRemark());
			}		
			if (!F.empty(ljzWithdrawLog.getPaymentNo())) {
				whereHql += " and t.paymentNo = :paymentNo";
				params.put("paymentNo", ljzWithdrawLog.getPaymentNo());
			}		
			if (!F.empty(ljzWithdrawLog.getCmmsAmt())) {
				whereHql += " and t.cmmsAmt = :cmmsAmt";
				params.put("cmmsAmt", ljzWithdrawLog.getCmmsAmt());
			}		
			if (!F.empty(ljzWithdrawLog.getRefType())) {
				whereHql += " and t.refType = :refType";
				params.put("refType", ljzWithdrawLog.getRefType());
			}		
			if (!F.empty(ljzWithdrawLog.getApplyLoginIp())) {
				whereHql += " and t.applyLoginIp = :applyLoginIp";
				params.put("applyLoginIp", ljzWithdrawLog.getApplyLoginIp());
			}
			if (ljzWithdrawLog.getAddtime() != null) {
				whereHql += " and to_days(t.addtime) = to_days(now())";
			}
		}	
		return whereHql;
	}

	@Override
	public void add(LjzWithdrawLog ljzWithdrawLog) {
		TljzWithdrawLog t = new TljzWithdrawLog();
		BeanUtils.copyProperties(ljzWithdrawLog, t);
		//t.setId(jb.absx.UUID.uuid());
		t.setIsdeleted(false);
		ljzWithdrawLogDao.save(t);
		ljzWithdrawLog.setId(t.getId());
	}

	@Override
	public LjzWithdrawLog get(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TljzWithdrawLog t = ljzWithdrawLogDao.get("from TljzWithdrawLog t  where t.id = :id", params);
		LjzWithdrawLog o = new LjzWithdrawLog();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(LjzWithdrawLog ljzWithdrawLog) {
		TljzWithdrawLog t = ljzWithdrawLogDao.get(TljzWithdrawLog.class, ljzWithdrawLog.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(ljzWithdrawLog, t, new String[] { "id" , "addtime", "isdeleted","updatetime" },true);
		}
	}

	@Override
	public void delete(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		ljzWithdrawLogDao.executeHql("update TljzWithdrawLog t set t.isdeleted = 1 where t.id = :id",params);
		//ljzWithdrawLogDao.delete(ljzWithdrawLogDao.get(TljzWithdrawLog.class, id));
	}

	@Override
	public LjzWithdrawLog get(LjzWithdrawLog log) {
		String hql = " from TljzWithdrawLog t ";
		@SuppressWarnings("unchecked")
		List<TljzWithdrawLog> l = query(hql, log, ljzWithdrawLogDao, "addtime", "desc");
		LjzWithdrawLog o = null;
		if (CollectionUtils.isNotEmpty(l)) {
			o = new LjzWithdrawLog();
			BeanUtils.copyProperties(l.get(0), o);
		}
		return o;
	}

	@Override
	public List<LjzWithdrawLog> query(LjzWithdrawLog log) {
		List<LjzWithdrawLog> ol = new ArrayList<>();
		String hql = " from TljzWithdrawLog t ";
		@SuppressWarnings("unchecked")
		List<TljzWithdrawLog> l = query(hql, log, ljzWithdrawLogDao, "addtime", "desc");
		if (CollectionUtils.isNotEmpty(l)) {
			for (TljzWithdrawLog t : l) {
				LjzWithdrawLog o = new LjzWithdrawLog();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		return ol;
	}

	@Override
	public void addAndBalance(LjzWithdrawLog withdrawLog) {
		add(withdrawLog);

		// 提现扣除钱包余额
		LjzBalanceLog balanceLog = new LjzBalanceLog();
		balanceLog.setUserId(withdrawLog.getUserId());
		balanceLog.setRefType(withdrawLog.getRefType());
		balanceLog.setRefId(withdrawLog.getId());
		balanceLog.setAmount(withdrawLog.getAmount().multiply(BigDecimal.valueOf(-1)));
		ljzBalanceLogService.addLogAndUpdateBalance(balanceLog);

		// 提现手续费扣除钱包余额
		if(withdrawLog.getServiceAmt() != null && withdrawLog.getServiceAmt().doubleValue() > 0) {
			balanceLog = new LjzBalanceLog();
			balanceLog.setUserId(withdrawLog.getUserId());
			balanceLog.setRefType("BBT008");
			balanceLog.setRefId(withdrawLog.getId());
			balanceLog.setAmount(withdrawLog.getServiceAmt().multiply(BigDecimal.valueOf(-1)));
			ljzBalanceLogService.addLogAndUpdateBalance(balanceLog);
		}
	}

	@Override
	public void editAudit(LjzWithdrawLog ljzWithdrawLog, String loginId, HttpServletRequest request) {
		LjzWithdrawLog withdrawLog = get(ljzWithdrawLog.getId());

		//通过
		if ("HS02".equals(ljzWithdrawLog.getHandleStatus())) {
			LjzUser user = ljzUserService.get(withdrawLog.getUserId());
			//2. 参数填充
			Map<String, Object> params = new HashMap<>();
			params.put("amount", withdrawLog.getAmount().multiply(BigDecimal.valueOf(100)).intValue());
			params.put("openid", user.getRefId());
			params.put("partner_trade_no", withdrawLog.getWithdrawNo());
			params.put("re_user_name", withdrawLog.getRealName());
			params.put("spbill_create_ip", IpUtil.getIp(request));

			try {
				//3. 扣款
				String requestXml = PayCommonUtil.requestTransfersXML(params);
				System.out.println("~~~~~~~~~~~~微信企业付款接口请求参数requestXml:" + requestXml);
				String result = HttpUtil.httpsRequestSSL(WeixinUtil.TRANSFERS_URL, requestXml);
				System.out.println("~~~~~~~~~~~~微信企业付款接口返回结果result:" + result);

				Map<String, String> resultMap = XMLUtil.doXMLParse(result);

				if (!F.empty(resultMap.get("result_code")) && resultMap.get("result_code").toString().equalsIgnoreCase("SUCCESS")) {
					//4. 编辑提现申请记录
					ljzWithdrawLog.setHandleLoginId(loginId);
					ljzWithdrawLog.setHandleTime(new Date());
					ljzWithdrawLog.setPaymentNo(resultMap.get("payment_no").toString());
					if(resultMap.get("cmms_amt") != null)
						ljzWithdrawLog.setCmmsAmt(new BigDecimal(resultMap.get("cmms_amt")).divide(BigDecimal.valueOf(100)));
					edit(ljzWithdrawLog);

				} else {
					ljzWithdrawLog.setHandleStatus("HS01");
					ljzWithdrawLog.setHandleLoginId(loginId);
					ljzWithdrawLog.setHandleRemark("提现失败" + resultMap.get("err_code_des"));
					edit(ljzWithdrawLog);
				}
			} catch (Exception e) {
				ljzWithdrawLog.setHandleStatus("HS01");
				ljzWithdrawLog.setHandleLoginId(loginId);
				ljzWithdrawLog.setHandleRemark("提现失败--接口异常");
				edit(ljzWithdrawLog);
			}
		}
		// 拒绝
		else if ("HS03".equals(ljzWithdrawLog.getHandleStatus())) {

			ljzWithdrawLog.setHandleLoginId(loginId);
			ljzWithdrawLog.setHandleTime(new Date());
			edit(ljzWithdrawLog);

			// 余额退回
			LjzBalanceLog balanceLog = new LjzBalanceLog();
			balanceLog.setUserId(withdrawLog.getUserId());
			balanceLog.setRefType("BBT009");
			balanceLog.setRefId(withdrawLog.getId());
			balanceLog.setAmount(withdrawLog.getAmount().add(withdrawLog.getServiceAmt()));
			balanceLog.setRemark("提现失败，余额退回");
			ljzBalanceLogService.addLogAndUpdateBalance(balanceLog);
			
		}
	}

}
