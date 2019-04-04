package dk.easv.friendsv2.Model;

import java.util.ArrayList;

public class Friends {

	ArrayList<BEFriend> m_friends;
	
	public Friends()
	{
		m_friends = new ArrayList<BEFriend>();
		m_friends.add(new BEFriend(1, "Alex", "123456", true));
		m_friends.add(new BEFriend(2, "Anni", "234567"));
		m_friends.add(new BEFriend(3, "Armandas", "126256", true));
		m_friends.add(new BEFriend(4, "Asger", "234567"));
		m_friends.add(new BEFriend(5, "Attila", "123456"));
		m_friends.add(new BEFriend(6, "Bastian", "994567"));
		m_friends.add(new BEFriend(7, "Bence", "127426"));
		m_friends.add(new BEFriend(8, "Bo", "204587", true));
		m_friends.add(new BEFriend(9, "Daniel", "123456"));
		m_friends.add(new BEFriend(10,"David", "234567", true));
		m_friends.add(new BEFriend(11, "Dominik", "123456"));
		m_friends.add(new BEFriend(12, "Jonas", "234567"));
		m_friends.add(new BEFriend(13, "Emil", "123456"));
		m_friends.add(new BEFriend(14, "Fabio", "234567", true));
		m_friends.add(new BEFriend(15, "Frederik", "123456"));
		m_friends.add(new BEFriend(16, "Jacob", "234567"));
		m_friends.add(new BEFriend(17, "Jan", "123456"));
		m_friends.add(new BEFriend(18, "Jesper", "234567"));
		m_friends.add(new BEFriend(19, "Kasper", "123456"));
		m_friends.add(new BEFriend(20, "kenneth", "234567", true));
		m_friends.add(new BEFriend(21, "Mads", "123456"));
		m_friends.add(new BEFriend(22, "Marius", "234567"));
		m_friends.add(new BEFriend(23, "Edwin", "234567"));
		m_friends.add(new BEFriend(24, "Michal", "123456"));
		m_friends.add(new BEFriend(25, "Morten", "234567", true));
		m_friends.add(new BEFriend(26, "Nicolai", "123456"));
		m_friends.add(new BEFriend(27, "Oliver", "234567"));
	}
	
	public ArrayList<BEFriend> getAll() {
		return m_friends;
	}
	
	public String[] getNames() {
		String[] res = new String[m_friends.size()];
		for (int i = 0; i < res.length; i++) {
			res[i] = m_friends.get(i).getName();
		}
		return res;
	}

	public void updateFriend(int pos, BEFriend friendUpdate) {
		m_friends.set(pos, friendUpdate);
	}

}
