package org.stockws.dao;

import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Repository;
import org.stockws.dao.iface.UserDao;
import org.stockws.po.CrmUser;

@Repository
public class UserDaoRedisImpl implements UserDao {

	@Override
	public CopyOnWriteArrayList<CrmUser> findByUsernameContaining(String term) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveUser(CrmUser user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public CrmUser findByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}
	
//	@Autowired
//	private RedisTemplate<String, String> redisTemplate;
//
//	@Resource(name = "redisTemplate")
//	private HashOperations<String, String, Object> hashOps;
//
//	@Resource(name = "redisTemplate")
//	private ZSetOperations<String, String> zsetOps;
//
//	@Resource(name = "redisTemplate")
//	private ValueOperations<String, String> valOps;
//	
//	@Override
//	public CrmUser findByUsername(String username) {
//		String sk_userName = String.format(NamingUtil.sk_userName_format, username);
//		String sk_user = valOps.get(sk_userName);
//		return sk_user==null? null : new ObjectMapper().convertValue(hashOps.entries(sk_user),CrmUser.class);
//	}
//	
//	
//	@Override
//	public void saveUser(CrmUser user) {
//		String sk_user = String.format(NamingUtil.sk_user_format, user.getId());
//		if (zsetOps.rank(NamingUtil.sk_users, sk_user) == null) {
//			zsetOps.add(NamingUtil.sk_users, sk_user, 0);
//		}
//		valOps.set(user.getUsername(), sk_user);
//		hashOps.putAll(sk_user, new ObjectMapper().convertValue(user, Map.class));
//	}
//	
//	@Override
//	public CopyOnWriteArrayList<CrmUser> findByUsernameContaining(String term) {
//		Set<String> keys = redisTemplate.keys("%"+term+"%");
//		CopyOnWriteArrayList<CrmUser> users = null;
//		if (keys!=null) {
//			users = new CopyOnWriteArrayList<CrmUser>();
//			for (String username : keys) {
//				users.add(findByUsername(username));
//			}
//		}
//		return users;
//	}
	
}
