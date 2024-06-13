package com.prosilion.superconductor.service.event;

import com.prosilion.superconductor.dto.TagPlugin;
import com.prosilion.superconductor.entity.join.EventEntityAbstractTagEntity;
import com.prosilion.superconductor.entity.AbstractTagEntity;
import com.prosilion.superconductor.repository.join.EventEntityAbstractTagEntityRepository;
import com.prosilion.superconductor.repository.AbstractTagEntityRepository;
import lombok.extern.slf4j.Slf4j;
import nostr.event.BaseTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ConcreteTagEntitiesService<
    P extends BaseTag,
    Q extends AbstractTagEntityRepository<R>,
    R extends AbstractTagEntity,
    S extends EventEntityAbstractTagEntity,
    U extends EventEntityAbstractTagEntityRepository<S>> {
  private final List<TagPlugin<P, Q, R, S, U>> tagPlugins;

  @Autowired
  public ConcreteTagEntitiesService(List<TagPlugin<P, Q, R, S, U>> tagPlugins) {
    this.tagPlugins = tagPlugins;
  }

  public List<AbstractTagEntity> getTags(Long eventId) {
    return tagPlugins.parallelStream().map(tagModule ->
            tagModule.getTags(eventId))
        .flatMap(List::stream).collect(Collectors.toList());
  }

  public void saveTags(Long eventId, List<P> baseTags) {
    tagPlugins.parallelStream().forEach(module ->
        baseTags.stream().filter(tags ->
                tags.getCode().equalsIgnoreCase(module.getCode()))
            .forEach(tag ->
                module.saveTag(eventId, tag)));
  }
}