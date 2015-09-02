package dijkstra.gen;

/**
 * This class is used for any exceptions thrown from the Dijkstra symbol table
 * operations.
 * 
 * @version Feb 7, 2015
 */
public class DijkstraCodeGeneratorException extends RuntimeException
{
	/**
	 * Sole constructor
	 * @param msg the message describing the error
	 */
	public DijkstraCodeGeneratorException(String msg)
	{
		super(msg);
		System.out.println(msg);
	}
}