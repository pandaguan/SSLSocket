package test.server1;

public class FileLogInfo {
	private long id;
	private String path;

	public FileLogInfo(long id, String path) {
		this.id = id;
		this.path = path;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
