package br.com.receitaquedoimenos.ReceitaQueDoiMenos.services;

import br.com.receitaquedoimenos.ReceitaQueDoiMenos.infra.exceptions.DataNotFoundException;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.drink.DrinkMapper;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.drink.DrinkRequestDTO;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.drink.DrinkResponseDTO;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.drink.TypeDrink;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.repositories.DrinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DrinkService {

    @Autowired
    DrinkRepository drinkRepository;

    @Autowired
    DrinkMapper drinkMapper;
    public DrinkResponseDTO createRecipe(DrinkRequestDTO drinkRequestDTO) {
        return drinkMapper.toResponseDTO(drinkRepository.save(drinkMapper.toEntity(drinkRequestDTO)));
    }

    public List<DrinkResponseDTO> getDrinksByName(String name) {
        return drinkRepository.findAllByNameIgnoreCase(name)
                .stream()
                .map(drinkMapper::toResponseDTO)
                .collect(Collectors.toList());
    }


    public List<DrinkResponseDTO> getDrinksByType(TypeDrink type) {
        return drinkRepository.findAllByTypeDrink(type)
                .stream()
                .map(drinkMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<DrinkResponseDTO> getAllDrinks() {
        return drinkRepository.findAll()
                .stream()
                .map(drinkMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public DrinkResponseDTO getDrinkById(String id){
        return drinkRepository.findById(id)
                .map(drinkInfo -> drinkMapper.toResponseDTO(drinkInfo))
                .orElseThrow(() -> new DataNotFoundException("Drink Not Found"));
    }
    public DrinkResponseDTO updateDrink(String id, DrinkRequestDTO drinkRequestDTO) {
        return drinkRepository.findById(id)
                .map(drinkFounded -> {
                    drinkFounded.setName(drinkRequestDTO.name());
                    drinkFounded.setType(drinkRequestDTO.type());
                    drinkFounded.setPhotoURL(drinkRequestDTO.photoURL());
                    drinkFounded.setVideoURL(drinkRequestDTO.videoURL());
                    drinkFounded.setInstructions(drinkRequestDTO.instructions());
                    drinkFounded.setIngredients(drinkRequestDTO.ingredients());

                    return drinkMapper.toResponseDTO(drinkRepository.save(drinkFounded));
                }).orElseThrow(() -> new DataNotFoundException("Drink Not Found"));
    }

    public void deleteDrink(String id) {
        drinkRepository.findById(id)
                        .ifPresentOrElse(value -> drinkRepository.delete(value),
                                () -> new DataNotFoundException("Drink Not Found"));
    }
}
