package obs.messages;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ResponseStatus {

	@XmlElement(name="status")
	public int status=0;

	@XmlElement(name="message")
	public String msg="";

	public ResponseStatus(){
		super();
	}

	public ResponseStatus(int status, String msg){
		super();
		this.status =status;
		this.msg=msg;
	}

	public ResponseStatus(ResponseStatus status2) {
		super();
		if(status2 != null)
		{
		this.status =status2.status;
		this.msg=status2.msg;
		}
	}
	
}
