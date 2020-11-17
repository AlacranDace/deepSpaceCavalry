package principal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class BaseDeDatos {
	
	Connection connect;
	private ArrayList<puntuacion> puntos = new ArrayList<puntuacion>();
	private ArrayList<puntuacion> topTen = new ArrayList<puntuacion>();

	public Connection Conectar(){
		
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		String url = "puntuaciones.sqlite";
		this.connect = null;
		try {
			this.connect = DriverManager.getConnection("jdbc:sqlite:"+url);
			if (this.connect!=null) {
					System.out.println("Conectado");
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			connect.setAutoCommit(false);	//LO PONGO AQUI PARA QUE NO MOLESTE, NO SE TRABAJAR CON EL
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return this.connect;
		
	}
	/*************PARA RESETEAR LA TABLA DE PUNTUACIONES**************/
//	public void ResetDTB(){
//		try {
//			Statement stmt = connect.createStatement();
//			String sql = "DELETE FROM puntuacion WHERE 1=1";
//			stmt.executeUpdate(sql);
//			stmt.close();
//			connect.commit();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
	public void Desconecta(){
		try {
			this.connect.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void ResetArrays(){
		this.puntos.clear();
		this.topTen.clear();
	}
	
	public void cargaPuntuaciones(){		//Coge lo que hay en la base de datos y lo carga en el arraylist
		
		
		try {
			Statement stmt = connect.createStatement();
			ResultSet rs = stmt.executeQuery( "SELECT * FROM puntuacion;" );
			
			while ( rs.next() ) {
				int punt  = rs.getInt("puntuacion");
				puntuacion aux = new puntuacion(punt);
				this.puntos.add(aux);
				//System.out.println(aux.getPuntos());
			}
			rs.close();
				
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void elige10(){
		/*Inicializamos el arraylist a 10 valores*/
		for (int i=0; i<10;i++){
			puntuacion top = new puntuacion(0);
			topTen.add(top);
		}
		/*Elegimos el topTen con este algoritmo*/
		for(int i=0; i<this.puntos.size();i++){
			puntuacion aux = puntos.get(i);
			if (aux.getPuntos() > topTen.get(9).getPuntos())
	        {
	            if (aux.getPuntos() > topTen.get(8).getPuntos())
	            {
	                if (aux.getPuntos() > topTen.get(7).getPuntos())
	                {
	                    if (aux.getPuntos() > topTen.get(6).getPuntos())
	                    {
	                        if (aux.getPuntos() > topTen.get(5).getPuntos())
	                        {
	                        	if (aux.getPuntos() > topTen.get(4).getPuntos())
		                        {
	                        		if (aux.getPuntos() > topTen.get(3).getPuntos())
	    	                        {
	                        			if (aux.getPuntos() > topTen.get(2).getPuntos())
	        	                        {
	                        				if (aux.getPuntos() > topTen.get(1).getPuntos())
	            	                        {
	                        					if (aux.getPuntos() > topTen.get(0).getPuntos())
	                	                        {
	                        						topTen.get(9).setPuntos(topTen.get(8).getPuntos());
	                        						topTen.get(8).setPuntos(topTen.get(7).getPuntos());
	                        						topTen.get(7).setPuntos(topTen.get(6).getPuntos());
	                        						topTen.get(6).setPuntos(topTen.get(5).getPuntos());
	                        						topTen.get(5).setPuntos(topTen.get(4).getPuntos());
	                        						topTen.get(4).setPuntos(topTen.get(3).getPuntos());
	                        						topTen.get(3).setPuntos(topTen.get(2).getPuntos());
	                        						topTen.get(2).setPuntos(topTen.get(1).getPuntos());
	                        						topTen.get(1).setPuntos(topTen.get(0).getPuntos());
	                        						topTen.get(0).setPuntos(aux.getPuntos());
	                	                        }
	                        					else
	                	                        {
	                	                        	topTen.get(9).setPuntos(topTen.get(8).getPuntos());
	                        						topTen.get(8).setPuntos(topTen.get(7).getPuntos());
	                        						topTen.get(7).setPuntos(topTen.get(6).getPuntos());
	                        						topTen.get(6).setPuntos(topTen.get(5).getPuntos());
	                        						topTen.get(5).setPuntos(topTen.get(4).getPuntos());
	                        						topTen.get(4).setPuntos(topTen.get(3).getPuntos());
	                        						topTen.get(3).setPuntos(topTen.get(2).getPuntos());
	                        						topTen.get(2).setPuntos(topTen.get(1).getPuntos());
	                        						topTen.get(1).setPuntos(aux.getPuntos());
	                	                        }
	            	                        } 
	                        				else
	                        				{
	                        					topTen.get(9).setPuntos(topTen.get(8).getPuntos());
                        						topTen.get(8).setPuntos(topTen.get(7).getPuntos());
                        						topTen.get(7).setPuntos(topTen.get(6).getPuntos());
                        						topTen.get(6).setPuntos(topTen.get(5).getPuntos());
                        						topTen.get(5).setPuntos(topTen.get(4).getPuntos());
                        						topTen.get(4).setPuntos(topTen.get(3).getPuntos());
                        						topTen.get(3).setPuntos(topTen.get(2).getPuntos());
                        						topTen.get(2).setPuntos(aux.getPuntos());
	                        				} 
	        	                        }
	                        			else 
	                        			{
	                        				topTen.get(9).setPuntos(topTen.get(8).getPuntos());
                    						topTen.get(8).setPuntos(topTen.get(7).getPuntos());
                    						topTen.get(7).setPuntos(topTen.get(6).getPuntos());
                    						topTen.get(6).setPuntos(topTen.get(5).getPuntos());
                    						topTen.get(5).setPuntos(topTen.get(4).getPuntos());
                    						topTen.get(4).setPuntos(topTen.get(3).getPuntos());
                    						topTen.get(3).setPuntos(aux.getPuntos());
	                        			}
	    	                        }
	                        		else
	                        		{
	                        			topTen.get(9).setPuntos(topTen.get(8).getPuntos());
                						topTen.get(8).setPuntos(topTen.get(7).getPuntos());
                						topTen.get(7).setPuntos(topTen.get(6).getPuntos());
                						topTen.get(6).setPuntos(topTen.get(5).getPuntos());
                						topTen.get(5).setPuntos(topTen.get(4).getPuntos());
                						topTen.get(4).setPuntos(aux.getPuntos());
	                        		}
	        	                }
	                        	else
	                        	{
	                        		topTen.get(9).setPuntos(topTen.get(8).getPuntos());
            						topTen.get(8).setPuntos(topTen.get(7).getPuntos());
            						topTen.get(7).setPuntos(topTen.get(6).getPuntos());
            						topTen.get(6).setPuntos(topTen.get(5).getPuntos());
            						topTen.get(5).setPuntos(aux.getPuntos());
	                        	}
	                        }
	                        else
	                        {
	                        	topTen.get(9).setPuntos(topTen.get(8).getPuntos());
        						topTen.get(8).setPuntos(topTen.get(7).getPuntos());
        						topTen.get(7).setPuntos(topTen.get(6).getPuntos());
        						topTen.get(6).setPuntos(aux.getPuntos());
	                        }
	                    }
	                    else
	                    {
	                    	topTen.get(9).setPuntos(topTen.get(8).getPuntos());
     						topTen.get(8).setPuntos(topTen.get(7).getPuntos());
     						topTen.get(7).setPuntos(aux.getPuntos());
	                    }
	                }
	                else
	                {
	                	topTen.get(9).setPuntos(topTen.get(8).getPuntos());
 						topTen.get(8).setPuntos(aux.getPuntos());
	                }
	            }
	            else
	            {
	            	topTen.get(9).setPuntos(aux.getPuntos());
	            }
	            
	        }
			
		}
		//this.puntos.clear();	//Borramos lo anterior y lo volvemos a cargar
		
	}
	
	public void altaPuntuacion(puntuacion aux){		//Introducimos la puntuación en la base de datos
		try {
			Statement stmt = connect.createStatement();
			//Vamos a realiar una inserción
			String sql = "INSERT INTO puntuacion (puntuacion) VALUES ('"+aux.getPuntos()+"');";
			//System.out.println("INSERT INTO puntuacion (puntuacion) VALUES ('"+aux.getPuntos()+"');");
			stmt.executeUpdate(sql);
			stmt.close();
			connect.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<puntuacion> getTopTen() {
		return topTen;
	}

	public void setTopTen(ArrayList<puntuacion> topTen) {
		this.topTen = topTen;
	}
}
