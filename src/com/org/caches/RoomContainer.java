package com.org.caches;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.org.interfaces.caches.Container;
import com.org.model.wx.AbstractRoom;
import com.org.model.wx.ChatingRoom;
import com.org.model.wx.StoryRoom;
import com.org.services.RoomService;
import com.org.utils.BeanUtils;

/**
 *
 */
public class RoomContainer implements Container{
	public static final Integer DEFAULT_ROOM_ID = 0;
	public static final Integer STORY_ROOM_ID = 1;
	private Map<Long, AbstractRoom> roomMap;
	private static RoomContainer temp;
	private RoomContainer(){}

	/**
	 * 获取用户信息
	 * @param openid
	 * @return
	 */
	public AbstractRoom getRoomById(Long roomId){
		if(roomMap.containsKey(roomId)) {
			return roomMap.get(roomId);
		}
		log.info("缓存无信息，执行数据库查询 roomId "+roomId);
		RoomService service = (RoomService)BeanUtils.getBean("roomService");
		// 保存，并返回数据库保存的用户信息
		JSONObject roomTemp = service.query(roomId);
		AbstractRoom ar = null;
		if(roomTemp.getBoolean("storymode")) {
			ar = (StoryRoom) JSONObject.toBean(roomTemp, StoryRoom.class);
		} else {
			ar = (ChatingRoom) JSONObject.toBean(roomTemp, ChatingRoom.class);
		}
		roomMap.put(roomTemp.getLong("roomid"), ar);
		return ar;
	}

	public void init(){
		roomMap = new HashMap<Long, AbstractRoom>();
		RoomService service = (RoomService)BeanUtils.getBean("roomService");
		// 保存，并返回数据库保存的用户信息
		JSONArray crlist = service.queryRoomList();
		if(crlist != null && crlist.size() > 0) {
			// 如果有，则添加进来
			JSONObject roomTemp = null;
			for (int i = 0; i < crlist.size(); i++) {
				roomTemp = crlist.getJSONObject(i);
				if(roomTemp.getBoolean("storymode")) {
					StoryRoom ar = (StoryRoom) JSONObject.toBean(roomTemp, StoryRoom.class);
					/*StoryRoom sr = new StoryRoom();
					sr.setRoomid(temp.getRoomid());
					sr.setRoomname(temp.getRoomname());
					sr.setRoomtitle(temp.getRoomtitle());
					sr.setStorymode(temp.isStorymode());
					sr.setTemplateid(temp.getTemplateid());*/
					roomMap.put(roomTemp.getLong("roomid"), ar);
				} else {
					ChatingRoom cr = (ChatingRoom) JSONObject.toBean(roomTemp, ChatingRoom.class);
					roomMap.put(roomTemp.getLong("roomid"), cr);
				}
			}
			log.info("已初始化聊天室信息"+ crlist.size() +"条");
		} else {
			// 如果没有房间的话，则创建一个默认的房间
			roomMap.put(Long.valueOf("0"), createDefaultRoom());
		}
	}
	
	private ChatingRoom createDefaultRoom(){
		Integer roomId = 0;
		// 房间名称
		String roomName = "快乐群";
		// 房间主题 
		String roomTitle = "开心啦";
		ChatingRoom cr = new ChatingRoom();
		cr.setRoomid(Long.valueOf(roomId));
		cr.setRoomname(roomName);
		cr.setRoomtitle(roomTitle);
		return cr;
	}
	
	public static RoomContainer getInstance(){
		if(temp == null) {
			temp = new RoomContainer();
		}
		return temp;
	}
	
	private static Log log = LogFactory.getLog(RoomContainer.class);
}
