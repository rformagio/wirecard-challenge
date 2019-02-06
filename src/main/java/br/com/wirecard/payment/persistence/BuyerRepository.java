package br.com.wirecard.payment.persistence;

import br.com.wirecard.payment.domain.Buyer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuyerRepository extends JpaRepository<Buyer, Long> {
}
