package csc366.jpademo.supplier;

import csc366.jpademo.Restaurant;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

@Entity  // indicates that this class maps to a database table
@Table(
        name = "transaction"     // may be omitted for default table naming
)
public class Transaction {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name="num_case")
    private int numCase;

    @Column
    private String notes;

    @OneToOne(
            targetEntity = SupplyContract.class,
            cascade = CascadeType.REMOVE,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "supply_contract_id")
    private SupplyContract supplyContract;

    @ManyToOne(
            targetEntity = Supplier.class,
            cascade = CascadeType.REMOVE,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @ManyToOne(
            targetEntity = Ingredient.class,
            cascade = CascadeType.PERSIST,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

    @ManyToOne(
            targetEntity = Restaurant.class,
            cascade = CascadeType.PERSIST,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    // TODO: Link Transation to DrinkVariation table
//    @ManyToOne(
//            targetEntity = DrinkVariation.class,
//            cascade = CascadeType.REMOVE,
//            fetch = FetchType.LAZY,
//            optional = false
//    )
//    private DrinkVariation drinkVariation;

    public Transaction() { }

    public Transaction(int numCase, String notes) {
        this.numCase = numCase;
        this.notes = notes;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public int getNumCase() {
        return this.numCase;
    }
    public void setNumCase(int numCase) {
        this.numCase = numCase;
    }

    public String getNotes() {
        return notes;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplyContract(SupplyContract supplyContract) {
        this.supplyContract = supplyContract;
    }

    public SupplyContract getSupplyContract() {
        return supplyContract;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public void setResturant(Restaurant restaurant) {
        this.restaurant = restaurant;
        restaurant.addTransaction(this);
    }

    public void removeResturant(Restaurant restaurant) {
        this.restaurant = null;
        restaurant.removeTransaction(this);
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void addSupplier(Supplier supplier) {
        supplier.addTransaction(this);
        this.supplier = supplier;
    }

    public void removeSupplier(Supplier supplier) {
        supplier.removeTransaction(this);
        this.supplier = null;
    }

    public void addSupplyContract(SupplyContract supplyContract) {
        this.supplyContract = supplyContract;
        supplyContract.addTransaction(this);
    }

    public void removeSupplyContract(SupplyContract supplyContract) {
        supplyContract.removeTransaction(this);
        this.supplyContract = null;
    }

    // TODO: Link Transaction to DrinkVariation table
//    public DrinkVariation getDrinkVariation() {
//        return drinkVariation;
//    }
//
//    public void setDrinkVariation(DrinkVariation dv) {
//        this.drinkVariation = dv;
//    }

    @Override
    public String toString() {
        // TODO: Add new relations here
        StringJoiner sj = new StringJoiner("," , SupplyContract.class.getSimpleName() + "[" , "]");
        sj.add(id.toString()).add(String.valueOf(numCase)).add(notes).add(String.valueOf(supplyContract)).add(String.valueOf(supplier)).add("ingredients="+String.valueOf(ingredient));
        return sj.toString();
    }

    @Override
    public int hashCode() {
        return 366;
    }
}
