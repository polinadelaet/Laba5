package response;

import message.EntityDTO;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "response")
@XmlRootElement
public final class ResponseDTO extends EntityDTO implements Serializable {
    public String status;
    public String answer;


    @Override
    public String toString() {
        return "ResponseDTO{" +
                "status='" + status + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }
}
