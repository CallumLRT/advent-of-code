import fileinput

def get_input(filename='input.txt') -> tuple[list[int], list[int]]:
    left_list = []
    right_list = []
    for line in fileinput.input(files=filename):
        line_chomp_split = line.rstrip("\n").split(" " * 3)
        left_list.append(int(line_chomp_split[0]))
        right_list.append(int(line_chomp_split[1]))
    
    return left_list, right_list

def solution_1() -> int:
    total_distances = 0
    left_list, right_list = get_input()
    left_list.sort()
    right_list.sort()

    for index, location in enumerate(left_list):
        total_distances += abs(location - right_list[index])
    
    return total_distances

def solution_2() -> int:
    similarity_score = 0
    calculated_similarities = {}
    left_list, right_list = get_input()
    
    for location in left_list:
        value = calculated_similarities.get(location, None)
        if value is not None: 
            similarity_score += value
        else:
            current_similarity = location * right_list.count(location)
            calculated_similarities[location] = current_similarity
            similarity_score += current_similarity
    
    return similarity_score

if __name__ == '__main__':
    print(solution_1())
    print(solution_2())