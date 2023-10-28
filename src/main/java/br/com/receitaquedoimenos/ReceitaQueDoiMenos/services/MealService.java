package br.com.receitaquedoimenos.ReceitaQueDoiMenos.services;

import br.com.receitaquedoimenos.ReceitaQueDoiMenos.infra.exceptions.DataNotFoundException;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.models.meal.*;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.repositories.MealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MealService {

    @Autowired
    MealRepository mealRepository;

    @Autowired
    MealMapper mealMapper;

    public MealResponseDTO createMeal(MealRequestDTO mealRequestDTO) {
        return mealMapper.toResponseDTO(mealRepository.save(mealMapper.toEntity(mealRequestDTO)));
    }

    public List<MealResponseDTO> getAll() {
        return mealRepository.findAll()
                .stream()
                .map(mealMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<MealResponseDTO> getMealsByName(String name) {
        return mealRepository.findAllByNameIgnoreCase(name)
                .stream()
                .map(mealMapper::toResponseDTO)
                .collect(Collectors.toList());
    }


    public List<MealResponseDTO> getMealsByTypeMeal(TypeMeal type) {
        return mealRepository.findAllByTypeMeal(type)
                .stream()
                .map(mealMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public MealResponseDTO updateMealInfo(String id, MealRequestDTO mealRequestDTO) {
        return mealRepository.findById(id)
                .map(mealFounded -> {
                    mealFounded.setName(mealRequestDTO.name());
                    mealFounded.setType(mealRequestDTO.type());
                    mealFounded.setPhotoURL(mealRequestDTO.photoURL());
                    mealFounded.setVideoURL(mealRequestDTO.videoURL());
                    mealFounded.setInstructions(mealRequestDTO.instructions());
                    mealFounded.setIngredients(mealRequestDTO.ingredients());

                    return mealMapper.toResponseDTO(mealRepository.save(mealFounded));
                }).orElseThrow(() -> new DataNotFoundException("Recipe Not Found"));
    }

    public void deleteMeal(String id) {
        mealRepository.findById(id)
                .ifPresentOrElse(meal -> {
                    mealRepository.delete(meal);
                }, () -> new DataNotFoundException("Recipe Not Found"));
    }
}
