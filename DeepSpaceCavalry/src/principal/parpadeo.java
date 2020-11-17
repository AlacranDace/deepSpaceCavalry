package principal;

public class parpadeo implements Runnable{
	
	private boolean parpadeo;
	private int descanso;
	
	public parpadeo(int descanso){
		this.descanso = descanso;
	}
	public void run() {
		while (true){
			this.parpadeo = true;
			try {
				Thread.sleep(this.descanso);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.parpadeo = false;
			try {
				Thread.sleep(this.descanso);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	public boolean isParpadeo() {
		return parpadeo;
	}
	public void setParpadeo(boolean parpadeo) {
		this.parpadeo = parpadeo;
	}
	public int getDescanso() {
		return descanso;
	}
	public void setDescanso(int descanso) {
		this.descanso = descanso;
	}

}
