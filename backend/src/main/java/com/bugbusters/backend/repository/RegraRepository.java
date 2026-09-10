package com.bugbusters.backend.repository;

import com.bugbusters.backend.model.Regra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegraRepository extends JpaRepository<Regra, Long> {
    Optional<Regra> findByCampanhaIdAndRemovidoEmIsNull(Long campanhaId);
}