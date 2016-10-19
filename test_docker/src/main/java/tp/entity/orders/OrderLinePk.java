package tp.entity.orders;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//------ lombok generation code annotations ------
@Getter @Setter
@ToString()
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
//------------------------------------------------

@Embeddable
public class OrderLinePk implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long orderId;

	private Integer lineNumber ;

}
