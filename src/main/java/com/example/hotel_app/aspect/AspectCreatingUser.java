package com.example.hotel_app.aspect;

import com.example.hotel_app.dto.UserDtoWriting;
import com.example.hotel_app.dtoConverter.UserMapper;
import com.example.hotel_app.model.AppUser;
import com.example.hotel_app.model.Booking;
import com.example.hotel_app.model.UserRoles;
import com.example.hotel_app.repository.BookingRepository;
import com.example.hotel_app.repository.UserRepository;
import com.example.hotel_app.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


/**
 * Отвечает за предобработку событий, расширяя возможности авторизации
 */
@Aspect
@Component
@RequiredArgsConstructor
public class AspectCreatingUser {

    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    /**
     * Проверяет существует ли пользователь в БД с такими username и email
     * @param newUser DTO для будущего {@link AppUser} передаваемого пользователем
     */
    @Before("execution(* com.example.hotel_app.controllers.UserController.createNewUser(..)) && args(newUser, ..)")
    public void isUserInDb(UserDtoWriting newUser) {
        AppUser user = userMapper.writerToUser(newUser, passwordEncoder);
        if (userRepository.existsByUsernameAndEmail(user.getUsername(), user.getEmail())) {
            throw new IllegalArgumentException("user with these username: " + user.getUsername() +
                    " and email: " + user.getEmail() + " exists");
        }
    }

    /**
     * Проверяет можно ли изменить пользователя. Пользователь может изменить либо самого себе,
     * либо изменить другого пользователя, если у инициировавшего есть роль ADMIN
     *
     * @param userForEditing DTO {@link UserDtoWriting} в котором передаются параметры для создания нового пользователя
     */
    @Before("execution(* com.example.hotel_app.controllers.UserController.editUser(..)) && args(userForEditing, ..)")
    public void hasSuitableRoleToEdit(UserDtoWriting userForEditing) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String usernameFromWriter = userForEditing.getUsername();
        CustomUserDetails userDetails;
        if (authentication.getPrincipal() instanceof CustomUserDetails) {
            userDetails = (CustomUserDetails) authentication.getPrincipal();
        } else {
            throw new UsernameNotFoundException("user recognition failed");
        }
        if (userDetails.getUsername().equals(usernameFromWriter) ||
                userDetails.getAuthorities().contains(new SimpleGrantedAuthority(UserRoles.ROLE_ADMIN.toString()))) {
            return;
        }
        throw new UsernameNotFoundException("method prohibited, no rights");
    }

    /**
     * Проверяет можно ли удалить пользователя. Пользователь может удалить либо самого себе,
     * либо удалить другого пользователя, если у инициировавшего есть роль ADMIN
     *
     * @param username Имя пользователя
     */
    @Before("execution(* com.example.hotel_app.controllers.UserController.deleteUser(..)) && args(username)")
    public void hasSuitableRoleToDelete(String username) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails;
        if (authentication.getPrincipal() instanceof CustomUserDetails) {
            userDetails = (CustomUserDetails) authentication.getPrincipal();
        } else {
            throw new UsernameNotFoundException("user recognition failed");
        }
        if (userDetails.getUsername().equals(username) ||
                userDetails.getAuthorities().contains(new SimpleGrantedAuthority(UserRoles.ROLE_ADMIN.toString()))) {
            return;
        }
        throw new UsernameNotFoundException("method prohibited, no rights");

    }

    /**
     * Проверяет может ли пользователь запросить доступ к брони.
     * Может если имеет роль ADMIN или является создателем брони
     *
     * @param id Идентификатор брони
     */
    @Before("execution(* com.example.hotel_app.controllers.BookingController.findById(..)) && args(id)" +
            "execution(* com.example.hotel_app.controllers.BookingController.deleteBooking(..)) && args(id)" +
            "execution(* com.example.hotel_app.controllers.BookingController.editBooking(..)) && args(id, ..)")
    public void isThisIsMyBooking(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails;
        if (authentication.getPrincipal() instanceof CustomUserDetails) {
            userDetails = (CustomUserDetails) authentication.getPrincipal();
        } else {
            throw new UsernameNotFoundException("user recognition failed");
        }
        if (bookingRepository.existsById(id)) {
            Booking booking = bookingRepository.findById(id).get();
            AppUser userFromBooking = booking.getUser();
            if (userFromBooking.getUsername().equals(userDetails.getUsername()) ||
                    userDetails.getAuthorities().contains(new SimpleGrantedAuthority(UserRoles.ROLE_ADMIN.toString()))) {
                return;
            }
            throw new UsernameNotFoundException("method prohibited, no rights");

        }
    }


}
