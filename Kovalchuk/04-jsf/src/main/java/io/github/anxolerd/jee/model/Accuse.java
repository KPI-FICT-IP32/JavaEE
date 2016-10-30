package io.github.anxolerd.jee.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "accuse")
@Data
@ToString(exclude = {"sinner"})
@NoArgsConstructor
@AllArgsConstructor
public class Accuse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    Long id;

    @ManyToOne
    @JoinColumn(name = "sinner_id", nullable = false, referencedColumnName = "id")
    Sinner sinner;

    @Column(name = "accuses", nullable = false)
    String accuses;
}
