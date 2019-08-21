package com.marco.temperaturecenter.db.data.location;

import lombok.*;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class Location implements Comparable<Location> {
	
	private String _id;
	public Location(String _id)
	{
		this._id=_id;
	}
	
	@Override
	public int compareTo(Location o) {
		
		if(this._id.equals(_id))
			return 0;
		return 1;
	}

	@Override
	public boolean equals(Object o) {
		if(this._id.equals(((Location)o).get_id()))
			return true;
		return false;
	}
}
