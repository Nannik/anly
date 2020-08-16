let gulp = require('gulp');
let sass = require('gulp-sass');
let browserSync = require('browser-sync').create();
let sourcemaps = require('gulp-sourcemaps');

const frontEndPath = "./src/test/front-end/";

gulp.task('sass-compile', () => {
    return gulp.src(frontEndPath + '/scss/**/*.scss')
        .pipe(sourcemaps.init())
        .pipe(sass().on('error', sass.logError))
        .pipe(sourcemaps.write("./map/"))
        .pipe(gulp.dest(frontEndPath + '/css'))
        .pipe(browserSync.stream());
})

gulp.task('serve', () => {
    browserSync.init({
        server: frontEndPath
    });

    gulp.watch(frontEndPath + "scss/", gulp.series("sass-compile"));
    gulp.watch(frontEndPath + "**/*.html").on("change", browserSync.reload);
    gulp.watch(frontEndPath + "js/**/*.js").on("change", browserSync.reload);
})