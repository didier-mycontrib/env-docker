package tp.entity.orders;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//------ lombok generation code annotations ------
@Getter @Setter
@ToString(exclude={"productRef"})
//@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
//------------------------------------------------

@Entity
@Table(name="orders.OrderLine")
public class OrderLine {
	
	@EmbeddedId
	private OrderLinePk pk;
	
	@ManyToOne
	@JoinColumn(name="productRef")
    private ProductRef productRef;
	
    private Integer quantity;
    
    public void setOrderId(Long orderId){
    	if(pk==null) {
    		pk=new OrderLinePk();
    	}
    	pk.setOrderId(orderId);
    }
    public void setLineNumber(Integer lineNumber){
    	if(pk==null) {
    		pk=new OrderLinePk();
    	}
    	pk.setLineNumber(lineNumber);
    }
    public Long getOrderId(){
    	return pk.getOrderId();
    }
    public Integer getLineNumber(){
    	return pk.getLineNumber();
    }

}
