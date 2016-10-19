package tp.entity.customers;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//------ lombok generation code annotations ------
@Getter @Setter
@ToString
//@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
//------------------------------------------------

@Entity
@Table(name="customers.Address")
public class Address {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String numberAndStreet;
	private String zip;
	private String town;
	private String country;
	

}
