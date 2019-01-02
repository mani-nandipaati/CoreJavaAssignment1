package com.cts.serialization.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.cts.serialization.pojo.Book;
import com.cts.serialization.pojo.Subject;
import com.cts.serialization.util.SerializationUtils;

public class SubjectDao {

	public void addSubject(Subject subject) {
		Object obj = SerializationUtils.deSerialize("subjectFile.ser");
		List<Subject> subjects;
		if(obj != null) {
			subjects = (List<Subject>) obj;
		}
		else {
			subjects = new ArrayList<>();
		}
		subjects.add(subject);
		SerializationUtils.serialize(subjects, "subjectFile.ser");
		System.out.println("Subject added succesfully");
	}

	public void deleteSubject(long id) {
		Object obj = SerializationUtils.deSerialize("subjectFile.ser");
		List<Subject> subjects;
		List<Subject> updatedSubjects = new ArrayList<>();
		boolean found = false;
		if(obj != null) {
			subjects = (List<Subject>) obj;
			for(Subject subject : subjects) {
				if(subject.getSubjectId() == id) {
					found = true;
					System.out.format("\n%10s %15s %15s ", "Id", "Title", "DurationInHours");
					System.out.format("\n%10d %15s %15d \n", subject.getSubjectId(), subject.getSubtitle(), subject.getDurationInHours());
					System.out.println("Subject Deleted Successfully");
				}else {
					updatedSubjects.add(subject);
				}
			}
		}
		if(found) {
			SerializationUtils.serialize(updatedSubjects, "subjectFile.ser");
		}
		else {
			System.out.println("Unable to find Subject with id "+ id);	
		}
	}

	public void searchSubject(long id) {
		Object obj = SerializationUtils.deSerialize("subjectFile.ser");
		List<Subject> subjects;
		boolean found = false;
		Set<Book> books;
		if(obj != null) {
			subjects = (List<Subject>) obj;
			for(Subject subject : subjects) {
				if(subject.getSubjectId() == id) {
					found = true;
					System.out.println("\nSubject Details");
					System.out.format("\n%10s %15s %15s ", "Id", "Title", "DurationInHours");
					System.out.format("\n%10d %15s %15d \n", subject.getSubjectId(), subject.getSubtitle(), subject.getDurationInHours());
					books = subject.getReferences();
					if(books != null && !books.isEmpty()) {
						System.out.println("\nReference Book Details");
						System.out.format("\n%10s %15s %15s %15s %15s", "Id", "Price", "Title", "Volume", "Publish Date");
						for(Book book : books) {
							System.out.format("\n%10d %15.2f %15s %15d %15s", book.getBookId(), book.getPrice(), book.getTitle(),
									book.getVolume(), book.getPublishDate());
						}
					}
				}
			}
		}
		if(!found) {
			System.out.println("Unable to find Subject with id "+ id);
		}
	}
	
	public List<Subject> getAllSubjects() {
		Object obj = SerializationUtils.deSerialize("subjectFile.ser");
		List<Subject> subjects = null;
		if(obj != null) {
			subjects = (List<Subject>) obj;
		}
		return subjects;
	}
	
	public void removeBookFomSubjectReferences(long bookId) {
		Object obj = SerializationUtils.deSerialize("subjectFile.ser");
		List<Subject> subjects = null;
		Set<Book> books;
		Set<Book> updatedBooks;
		if(obj != null) {
			subjects = (List<Subject>) obj;
			for(Subject subject : subjects) {
				books = subject.getReferences();
				if(books != null && !books.isEmpty()) {
					updatedBooks = new HashSet<>();
					for(Book book : books) {
						if(book.getBookId() != bookId) {
							updatedBooks.add(book);	
						}
					}
					subject.setReferences(updatedBooks);
				}
			}
			SerializationUtils.serialize(subjects, "subjectFile.ser");
		}
	}	
	
}
