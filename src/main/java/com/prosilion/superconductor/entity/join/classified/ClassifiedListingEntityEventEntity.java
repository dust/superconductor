package com.prosilion.superconductor.entity.join.classified;

import com.prosilion.superconductor.entity.join.generic.EventEntityGenericTagEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "classified_listing-event-join")
public class ClassifiedListingEntityEventEntity extends EventEntityGenericTagEntity implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long eventId;
  private Long classifiedListingId;

  public ClassifiedListingEntityEventEntity(Long eventId, Long classifiedListingId) {
    this.eventId = eventId;
    this.classifiedListingId = classifiedListingId;
  }

  @Override
  public Long getLookupId() {
    return classifiedListingId;
  }
}