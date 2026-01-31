public class JobMapper {

    public static JobResponse toResponse(Job job) {
        return new JobResponse(
                job.getId(),
                job.getTitle(),
                job.getDescription(),
                job.getCompany(),
                job.getCreatedBy().getName()
        );
    }
}
