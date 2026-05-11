package util;

import model.Identifiable;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Registre<T extends Identifiable> {

    private final Map<String, T> elements;

    public Registre() {
        this.elements = new LinkedHashMap<>();
    }

    public void ajouter(T e) {
        if (elements.containsKey(e.getIdentifiant())) {
            throw new IllegalArgumentException("Identifiant deja existant : " + e.getIdentifiant());
        }
        elements.put(e.getIdentifiant(), e);
    }

    public boolean supprimer(String id) {
        return elements.remove(id) != null;
    }

    public T trouver(String id) {
        return elements.get(id);
    }

    public List<T> tout() {
        return new ArrayList<>(elements.values());
    }

    public List<T> filtrer(Predicate<? super T> p) {
        return elements.values().stream().filter(p).collect(Collectors.toList());
    }

    public int taille() { return elements.size(); }

    public boolean contient(String id) { return elements.containsKey(id); }

    public Collection<T> values() { return elements.values(); }
}
