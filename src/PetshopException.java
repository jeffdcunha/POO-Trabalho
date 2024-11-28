public class PetshopException extends Exception {
    public PetshopException(String message) { 
        super(message);
    }

    public PetshopException() { 
        super();
    }

    public PetshopException( Throwable t ) { 
        super(t);
    }
}
