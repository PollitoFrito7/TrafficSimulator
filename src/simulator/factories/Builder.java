package simulator.factories;

import org.json.JSONObject;

public abstract class Builder<T> {
	private String _type_tag;	// matches the type field of JSON struct
	private String _desc;	// descr obj created by this builder

	public Builder(String typeTag, String desc) {
		if (typeTag == null || desc == null || typeTag.isBlank() || desc.isBlank())
			throw new IllegalArgumentException("Invalid type/desc");

		_type_tag = typeTag;
		_desc = desc;
	}

	public String get_type_tag() {
		return _type_tag;
	}

	public JSONObject get_info() {
		JSONObject info = new JSONObject();
		info.put("type", _type_tag);
		info.put("desc", _desc);

		JSONObject data = new JSONObject();
		fill_in_data(data);
		info.put("data", data);
		return info;
	}

<<<<<<< Updated upstream
	// subclasses override this method to fill in 
	protected void fill_in_data(JSONObject o) {
=======
	protected void fill_in_Data(JSONObject o) {
		
>>>>>>> Stashed changes
	}

	@Override
	public String toString() {
		return _desc;
	}

	protected abstract T create_instance(JSONObject data);
}
