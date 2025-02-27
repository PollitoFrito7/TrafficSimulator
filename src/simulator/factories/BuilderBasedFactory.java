package simulator.factories;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

public class BuilderBasedFactory<T> implements Factory<T> {
	private Map<String, Builder<T>> _builders;
	private List<JSONObject> _builders_info;

	public BuilderBasedFactory() {
		// create hashMap for _builders
		_builders = new HashMap<String, Builder<T>>();
		// create a linkedList for _builders_info
		_builders_info = new LinkedList<JSONObject>();
	}

	public BuilderBasedFactory(List<Builder<T>> builders) {
		this();

		// call add_builder(b) for each builder b in builder
		for (Builder<T> b : builders) {
			addBuilder(b);
		}
	}

	public void addBuilder(Builder<T> b) {
		_builders.put(b.get_type_tag(), b);
		_builders_info.add(b.get_info());
	}

	@Override
	public T create_instance(JSONObject info) {
		if (info == null) {
			throw new IllegalArgumentException("'info' cannot be null");
		}

		String type = info.getString("type");
		Builder<T> builder = _builders.get(type);
		if (builder != null) {
			JSONObject data = info.has("data") ? info.getJSONObject("data") : new JSONObject();
			T instance = builder.create_instance(data);
			if (instance != null) {
				return instance;
			}
		}

		throw new IllegalArgumentException("Unrecognized 'info': " + info.toString());
	}

	@Override
	public List<JSONObject> get_info() {
		return Collections.unmodifiableList(_builders_info);
	}

}
