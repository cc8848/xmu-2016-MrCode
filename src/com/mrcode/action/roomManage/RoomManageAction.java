package com.mrcode.action.roomManage;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.mrcode.base.BaseAction;
import com.mrcode.common.ViewLocation;
import com.mrcode.model.Customer;
import com.mrcode.model.Password;
import com.mrcode.model.Room;
import com.mrcode.service.PasswordService;
import com.mrcode.service.RoomService;
import com.opensymphony.xwork2.ActionContext;

@Controller
@ParentPackage("customers")
@Namespace("/customer")
public class RoomManageAction extends BaseAction<Room>{
	
	
	@Autowired
	private PasswordService passwordService;
	
	@Autowired
	private RoomService roomService;
	
	//登录页面
		@Action(value = "toRoomManageFail")
		public void toRoomManageFail() throws Exception{
			if(ActionContext.getContext().get("msg")!=null)
				request.setAttribute("msg", ActionContext.getContext().get("msg"));
			Customer cus = (Customer) session.get("customer");
			String phoneNumber = cus.getPhoneNumber();
			System.out.println("phoneNumber--" + phoneNumber );
			
			//1、先根据该用户电话号码得到，password对象
			
			Password passwd = passwordService.getPasswordByPhone (phoneNumber);	
			
			if(passwd == null) {
				// 代表无可用房间
				writeStringToResponse("1");
			} else 
				writeStringToResponse("0");
			
		
		}
		
		@Action(value = "toRoomManage", results = { @Result(name = "toRoomManage", location = ViewLocation.View_ROOT
				+ "management.jsp") })
		public String toRoomManage() throws Exception{
			if(ActionContext.getContext().get("msg")!=null)
				request.setAttribute("msg", ActionContext.getContext().get("msg"));
			Customer cus = (Customer) session.get("customer");
			String phoneNumber = cus.getPhoneNumber();
			System.out.println("phoneNumber--" + phoneNumber );
			
			//1、先根据该用户电话号码得到，password对象
			
			Password passwd = passwordService.getPasswordByPhone (phoneNumber);	
			
//			if(passwd == null) {
//				
//				writeStringToResponse("1");
//			}
			
			System.out.println("输出Password id--" + passwd.getId());
			//2、根据该password对象，得到roomId
			Room room = passwd.getRoom();
			System.out.println(room.getId());
			
//			懒加载：System.out.println("获取hotel--"  + room.getRoomtype().getHotel().getName());
			
			//3、根据roomId 获取房间类型
			//RoomType roomtype = roomService.getRoomTypeByRoomId();
			 request.setAttribute("customer", cus);
			 request.setAttribute("roomid", room.getId());
			 request.setAttribute("roomnumber", room.getRoomNumber());
			return "toRoomManage";
		}
		
		//订餐页面
		@Action(value = "toRoomManageOrderFood", results = { @Result(name = "roomManageOrderFoodUI", location = ViewLocation.View_ROOT
				+ "roommanage_orderfood.jsp") })
		public String toRoomManageOrderFood() throws Exception{
			return "roomManageOrderFoodUI";
		}
		//日用品页面
		@Action(value = "toRoomManageBuy", results = { @Result(name = "roomManageBuyUI", location = ViewLocation.View_ROOT
				+ "roommanage_buy.jsp") })
		public String toRoomManageBuy() throws Exception{
			return "roomManageBuyUI";
		}
}
