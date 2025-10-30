# Specification-based Testing

## 1. Goal, inputs and outputs
- Goal: Verify that Clyde will make the good move
- Input domain: Map, Clyde, Pacman
- Output domain: Direction or Null

## 2. Explore the program (if needed)

## 3. Identify input and output partitions

Partition: ensemble de valeur qui font que le programme se comportera de la même manière

### Input partitions

#### Individual inputs
- P1: Pac-Man far away (>8)
- P2: Pac-Man close (≤8)
- P3: No Pac-Man
- P4: Walls between Clyde and Pac-Man

#### Combinations of input values

### Output partitions
- Move toward Pac-Man
- Move opposite direction
- No move

## 4. Identify boundaries
- Distance = 8 → border case
- Distance = 9 → chase
- Distance = 7 → flee

## 5. Select test cases
