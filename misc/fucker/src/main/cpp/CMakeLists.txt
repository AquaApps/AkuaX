cmake_minimum_required(VERSION 3.22.1)

project("fucker")

add_library(${CMAKE_PROJECT_NAME} SHARED
        fucker.cpp)

target_link_libraries(${CMAKE_PROJECT_NAME})