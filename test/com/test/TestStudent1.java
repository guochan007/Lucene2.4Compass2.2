package com.test;

import org.compass.core.Compass;
import org.compass.core.CompassHighlighter;
import org.compass.core.CompassHits;
import org.compass.core.CompassSession;
import org.compass.gps.CompassGps;
import org.compass.gps.CompassGpsDevice;
import org.compass.gps.device.hibernate.HibernateGpsDevice;
import org.compass.gps.impl.SingleCompassGps;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.pojo.Students;
import com.util.CompassUtil;
import com.util.HibernateSessionFactory;

public class TestStudent1 {
	private static Session session = null;
	private static Transaction transaction = null;
	private static SessionFactory sessionFactory=null; 
    private Compass compass = null;  
    private CompassSession compassSession;

   	@Before
	public void init() {
		// 会话对象
		session = HibernateSessionFactory.getSession();
		// 用这个工具比较好，哪里出错，有提示，而其他的方法没有他好用
		sessionFactory=HibernateSessionFactory.getSessionFactory();
		
		compass=CompassUtil.getCompass();
		compassSession= compass.openSession();
		
		// 开启事务
        transaction=session.beginTransaction();
		 
	}

	@After
	public void destory() {
		session.close();
		sessionFactory.close();
		compassSession.close();
	}

	@Test
	public void test() {
		System.out.println("11111");
	}
	
//	保存
	@Test
	public void testSaveStudent() {
		try{
			Students students1 = new Students("习大大", "北京中南海");
			session.save(students1);
			Students students2 = new Students("小李习大大王", "中国人北京郊区中南海军的实力");
			session.save(students2);
			Students students3 = new Students("小张", "太原小店恒大绿洲");
			session.save(students3);
			Students students4 = new Students("彭丽媛", "北京习大大家里住着");
			session.save(students4);
			Students students5 = new Students("小李", "大同广灵");
			session.save(students5);
			Students students6 = new Students("大王", "大同广灵");
			session.save(students6);
			Students students7 = new Students("小何", "山西代县新高乡韩街村");
			session.save(students7);
			
			transaction.commit();
		}catch(Exception e){
			e.printStackTrace();
			transaction.rollback();
		}
	}

//	查询
	@Test
	public void testFindFromCompass() {
		String keyword="大";
        CompassHits hits = compassSession.find("username:" + keyword + " OR address:" + keyword);  
        for (int i = 0; i < hits.length(); i++) {
        	Students obj = (Students) hits.data(i);
        	CompassHighlighter hignlighter = hits.highlighter(i);  
            String username = hignlighter.fragment("username");  
            String address = hignlighter.fragment("address");  
            if(username!=null && !"".equals(username)){  
               obj.setUsername(username);  
            }  
            if(address!=null && !"".equals(address)){  
            	obj.setAddress(address);  
            }  

            System.out.println(obj.toString());
        }
    }
	
//	重建  更新  索引 
	@Test
	public void testIndexUserFromDb() {
        CompassGps gps = new SingleCompassGps(compass);

        CompassGpsDevice device1 = new HibernateGpsDevice("h", sessionFactory);
        device1.setName("device1");
        gps.addGpsDevice(device1);

        gps.start();
        gps.index(Students.class);
        gps.stop();

    }

}
